package com.my.testing.controller.actions.implementation.base;

import com.my.testing.controller.actions.Action;
import jakarta.servlet.http.HttpServletRequest;

import static com.my.testing.controller.actions.constants.Pages.SIGN_IN_PAGE;
import static com.my.testing.controller.actions.constants.Parameters.LOGGED_USER;

public class SignOutAction implements Action {
    @Override
    public String execute(HttpServletRequest request) {
        if (request.getSession().getAttribute(LOGGED_USER) != null) {
            request.getSession().invalidate();
        }
        return SIGN_IN_PAGE;
    }
}
