package com.my.testing.controller.listeners;

import com.my.testing.controller.context.AppContext;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebListener;
import org.apache.logging.log4j.*;

@WebListener
public class ContextListener implements ServletContextListener {
    private static final Logger logger = LogManager.getLogger(ContextListener.class);
    private static final String PROPERTIES_FILE = "context.properties";

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        AppContext.createAppContext(PROPERTIES_FILE);
        logger.info("AppContext is set");
    }
}
