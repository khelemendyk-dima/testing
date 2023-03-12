package com.my.testing.controller;

import com.my.testing.controller.actions.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.apache.logging.log4j.*;

import java.io.IOException;

import static com.my.testing.controller.actions.constants.Pages.ERROR_PAGE;
import static com.my.testing.controller.actions.constants.Parameters.ACTION;

/**
 * Controller class. Implements Front-controller pattern. Chooses action to execute and redirect or forward result.
 *
 * @author Khelemendyk Dmytro
 * @version 1.0
 */
@WebServlet("/controller")
public class Controller extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(Controller.class);
    private static final ActionFactory ACTION_FACTORY = ActionFactory.getActionFactory();

    /**
     * Calls and executes action and then forwards requestDispatcher
     * @param request comes from user
     * @param response comes from user
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher(process(request, response)).forward(request, response);
    }

    /**
     * Calls and executes action, then sendRedirect for PRG pattern implementation
     * @param request comes from user
     * @param response comes from user
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect(process(request, response));
    }

    /**
     * Obtains path to user in doPost/doGet methods. In case of error will return error page
     * @return path
     */
    private String process(HttpServletRequest request, HttpServletResponse response) {
        Action action = ACTION_FACTORY.createAction(request.getParameter(ACTION));
        String path = ERROR_PAGE;
        try {
            path = action.execute(request, response);
        } catch (Exception e) {
            logger.error(String.format("Couldn't execute action because of %s", e.getMessage()));
        }

        return path;
    }
}
