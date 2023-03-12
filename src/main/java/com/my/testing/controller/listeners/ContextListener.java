package com.my.testing.controller.listeners;

import com.my.testing.controller.context.AppContext;
import com.mysql.cj.jdbc.AbandonedConnectionCleanupThread;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebListener;
import org.apache.logging.log4j.*;

import java.sql.*;

/**
 * ContextListener class.
 *
 * @author Khelemendyk Dmytro
 * @version 1.0
 */
@WebListener
public class ContextListener implements ServletContextListener {
    private static final Logger logger = LogManager.getLogger(ContextListener.class);
    /** Name of properties file to configure DataSource, EmailSender and Captcha */
    private static final String PROPERTIES_FILE = "context.properties";

    /**
     * Creates AppContext and passes ServletContext and properties to initialize all required classes
     * @param sce passed by application
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger.info("The application has started");
        try {
            AppContext.createAppContext(sce.getServletContext(), PROPERTIES_FILE);
            logger.info("AppContext was set");
        } catch (Exception e) {
            logger.error("AppContext wasn't set");
        }
    }

    /**
     * Closes MySQL thread and deregister all drivers
     * @param sce passed by application
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        AbandonedConnectionCleanupThread.checkedShutdown();
        deregisterDrivers();
        logger.info("The application has stopped working");
    }

    private void deregisterDrivers() {
        DriverManager.drivers().forEach(driver -> {try {
            DriverManager.deregisterDriver(driver);
        } catch (SQLException e) {
            logger.warn(String.format("Couldn't deregister %s", driver), e);
        }
        });
    }
}
