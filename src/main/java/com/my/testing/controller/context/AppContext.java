package com.my.testing.controller.context;

import com.my.testing.model.connection.MyDataSource;
import com.my.testing.model.dao.DAOFactory;
import lombok.Getter;
import com.my.testing.model.services.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sql.DataSource;
import java.io.InputStream;
import java.util.Properties;

@Getter
public class AppContext {
    private static final Logger logger = LogManager.getLogger(AppContext.class);
    private static AppContext appContext;

    private final UserService userService;
    private final TestService testService;
    private final QuestionService questionService;
    private final AnswerService answerService;
    private final TestResultService testResultService;

    private AppContext(String propertiesFile) {
        Properties properties = getProperties(propertiesFile);
        DataSource dataSource = MyDataSource.getDataSource(properties);
        DAOFactory daoFactory = DAOFactory.getInstance(dataSource);
        ServiceFactory serviceFactory = ServiceFactory.getInstance(daoFactory);
        userService = serviceFactory.getUserService();
        testService = serviceFactory.getTestService();
        questionService = serviceFactory.getQuestionService();
        answerService = serviceFactory.getAnswerService();
        testResultService = serviceFactory.getTestResultService();
    }

    public static AppContext getAppContext() {
        return appContext;
    }

    public static void createAppContext(String propertiesFile) {
        appContext = new AppContext(propertiesFile);
    }

    private static Properties getProperties(String propertiesFile) {
        Properties properties = new Properties();
        try (InputStream resource = AppContext.class.getClassLoader().getResourceAsStream(propertiesFile)){
            properties.load(resource);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return properties;
    }
}
