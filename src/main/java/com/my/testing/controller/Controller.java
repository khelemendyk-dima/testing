package com.my.testing.controller;

import com.my.testing.controller.actions.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.apache.logging.log4j.*;

import java.io.IOException;

import static com.my.testing.controller.actions.constants.Pages.ERROR_PAGE;
import static com.my.testing.controller.actions.constants.Parameters.ACTION;

@WebServlet("/controller")
public class Controller extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(Controller.class);
    private static final ActionFactory ACTION_FACTORY = ActionFactory.getActionFactory();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher(process(request)).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect(process(request));
    }

    private String process(HttpServletRequest request) {
        Action action = ACTION_FACTORY.createAction(request.getParameter(ACTION));
        String path = ERROR_PAGE;
        try {
            path = action.execute(request);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return path;
    }
}
