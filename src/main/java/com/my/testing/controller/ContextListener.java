package com.my.testing.controller;

import com.my.testing.controller.context.AppContext;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebListener;
import org.apache.logging.log4j.*;

@WebListener
public class ContextListener implements ServletContextListener {
    private static final Logger logger = LogManager.getLogger(ContextListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        AppContext.getAppContext();
        logger.info("AppContext is set");
    }
}
