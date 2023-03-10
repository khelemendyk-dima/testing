package com.my.testing.controller.context;

import com.my.testing.model.connection.MyDataSource;
import com.my.testing.model.dao.DAOFactory;
import com.my.testing.utils.*;
import jakarta.servlet.ServletContext;
import lombok.Getter;
import com.my.testing.model.services.*;
import org.apache.logging.log4j.*;

import javax.sql.DataSource;
import java.io.InputStream;
import java.util.Properties;

/**
 * AppContext class. Contains all required to correct application work objects
 *
 * @author Khelemendyk Dmytro
 * @version 1.0
 */
@Getter
public class AppContext {
    private static final Logger logger = LogManager.getLogger(AppContext.class);
    private static AppContext appContext;
    private final UserService userService;
    private final TestService testService;
    private final QuestionService questionService;
    private final AnswerService answerService;
    private final TestResultService testResultService;
    private final CaptchaUtil captcha;
    private final PdfUtil pdfUtil;
    private final EmailSenderUtil emailSender;

    private AppContext(ServletContext servletContext, String propertiesFile) {
        pdfUtil = new PdfUtil(servletContext);
        Properties properties = getProperties(propertiesFile);
        emailSender = new EmailSenderUtil(properties);
        captcha = new CaptchaUtil(properties);
        DataSource dataSource = MyDataSource.getDataSource(properties);
        DAOFactory daoFactory = DAOFactory.getInstance(dataSource);
        ServiceFactory serviceFactory = ServiceFactory.getInstance(daoFactory);
        userService = serviceFactory.getUserService();
        testService = serviceFactory.getTestService();
        questionService = serviceFactory.getQuestionService();
        answerService = serviceFactory.getAnswerService();
        testResultService = serviceFactory.getTestResultService();
    }

    /**
     * @return instance of AppContext
     */
    public static AppContext getAppContext() {
        return appContext;
    }

    /**
     * Creates instance of AppContext to user in Actions. Configure all required classes. Loads properties
     * @param servletContext to user relative address in classes
     * @param propertiesFile to configure DataSource, EmailSender and Captcha
     */
    public static void createAppContext(ServletContext servletContext, String propertiesFile) {
        appContext = new AppContext(servletContext, propertiesFile);
    }

    private static Properties getProperties(String propertiesFile) {
        Properties properties = new Properties();

        try (InputStream resource = AppContext.class.getClassLoader().getResourceAsStream(propertiesFile)){
            properties.load(resource);
        } catch (Exception e) {
            logger.error(String.format("AppContext couldn't read properties because of %s", e.getMessage()));
        }

        return properties;
    }
}
