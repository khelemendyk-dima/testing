package com.my.testing.controller.actions.implementation.user;

import com.my.testing.controller.actions.Action;
import com.my.testing.exceptions.ServiceException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.time.LocalDateTime;

import static com.my.testing.controller.actions.ActionUtil.*;
import static com.my.testing.controller.actions.constants.ActionNames.START_TEST_ACTION;
import static com.my.testing.controller.actions.constants.Parameters.*;

public class StartTestAction implements Action {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        return isPostMethod(request) ? executePost(request) : executeGet(request);
    }

    private String executeGet(HttpServletRequest request) {
        return getPath(request);
    }

    private String executePost(HttpServletRequest request) {
        String path = "controller?action=solve-test&id=" + request.getParameter(ID);
        int duration = Integer.parseInt(request.getParameter(DURATION));

        request.getSession().setAttribute(FINISH_TEST_TIME, LocalDateTime.now().plusMinutes(duration));
        request.getSession().setAttribute(CURRENT_PATH, path);

        return getActionToRedirect(START_TEST_ACTION);
    }
}
