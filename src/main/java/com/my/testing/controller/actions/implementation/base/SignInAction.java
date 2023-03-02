package com.my.testing.controller.actions.implementation.base;

import com.my.testing.controller.actions.Action;
import com.my.testing.controller.context.AppContext;
import com.my.testing.dto.UserDTO;
import com.my.testing.exceptions.*;
import com.my.testing.model.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static com.my.testing.controller.actions.ActionUtil.*;
import static com.my.testing.controller.actions.constants.ActionNames.SIGN_IN_ACTION;
import static com.my.testing.controller.actions.constants.Pages.*;
import static com.my.testing.controller.actions.constants.Parameters.*;

public class SignInAction implements Action {
    private final UserService userService;

    public SignInAction(AppContext appContext) {
        userService = appContext.getUserService();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        return isPostMethod(request) ? executePost(request) : executeGet(request);
    }

    private String executeGet(HttpServletRequest request) {
        transferStringFromSessionToRequest(request, EMAIL);
        transferStringFromSessionToRequest(request, ERROR);
        return getPath(request);
    }

    private String executePost(HttpServletRequest request) throws ServiceException {
        String path = PROFILE_PAGE;
        String email = request.getParameter(EMAIL);
        String password = request.getParameter(PASSWORD);
        try {
            setLoggedUser(request, userService.signIn(email, password));
        } catch (NoSuchUserException | IncorrectPasswordException e) {
            request.getSession().setAttribute(ERROR, e.getMessage());
            request.getSession().setAttribute(EMAIL, email);
            path = SIGN_IN_PAGE;
        }
        request.getSession().setAttribute(CURRENT_PATH, path);
        return getActionToRedirect(SIGN_IN_ACTION);
    }

    private static void setLoggedUser(HttpServletRequest request, UserDTO user) {
        request.getSession().setAttribute(LOGGED_USER, user);
        request.getSession().setAttribute(ROLE, user.getRole());
    }
}
