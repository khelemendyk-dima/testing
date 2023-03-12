package com.my.testing.controller.actions.implementation.base;

import com.my.testing.controller.actions.Action;
import jakarta.servlet.http.*;
import org.apache.logging.log4j.*;

import static com.my.testing.controller.actions.constants.Pages.INDEX_PAGE;

/**
 * This is DefaultAction class. Usually called if there is a mistake in action name
 *
 * @author Khelemendyk Dmytro
 * @version 1.0
 */
public class DefaultAction implements Action {
    private static final Logger logger = LogManager.getLogger(DefaultAction.class);

    /**
     * @return index page
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        logger.info("For some reason default action was called");
        return INDEX_PAGE;
    }
}
