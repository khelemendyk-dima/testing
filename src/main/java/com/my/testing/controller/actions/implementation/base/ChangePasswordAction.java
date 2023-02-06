package com.my.testing.controller.actions.implementation.base;

import com.my.testing.controller.actions.Action;
import com.my.testing.controller.context.AppContext;
import com.my.testing.dto.UserDTO;
import com.my.testing.exceptions.*;
import com.my.testing.model.services.UserService;
import jakarta.servlet.http.HttpServletRequest;

import static com.my.testing.controller.actions.ActionUtil.*;
import static com.my.testing.controller.actions.constants.Pages.CHANGE_PASSWORD_PAGE;
import static com.my.testing.controller.actions.constants.ParameterValues.SUCCEED_UPDATED;
import static com.my.testing.controller.actions.constants.Parameters.*;
import static com.my.testing.controller.actions.constants.ActionNames.CHANGE_PASSWORD_ACTION;

public class ChangePasswordAction implements Action {
    private final UserService userService;

    public ChangePasswordAction(AppContext appContext) {
        userService = appContext.getUserService();
    }

    @Override
    public String execute(HttpServletRequest request) throws ServiceException {
        return isPostMethod(request) ? executePost(request) : executeGet(request);
    }

    private String executeGet(HttpServletRequest request) {
        transferStringFromSessionToRequest(request, MESSAGE);
        transferStringFromSessionToRequest(request, ERROR);
        return CHANGE_PASSWORD_PAGE;
    }

    private String executePost(HttpServletRequest request) throws ServiceException {
        try {
            long id = ((UserDTO) request.getSession().getAttribute(LOGGED_USER)).getId();
            String oldPassword = request.getParameter(OLD_PASSWORD);
            String password = request.getParameter(PASSWORD);
            String confirmPassword = request.getParameter(CONFIRM_PASSWORD);

            userService.changePassword(id, oldPassword, password, confirmPassword);
            request.getSession().setAttribute(MESSAGE, SUCCEED_UPDATED);
        } catch (IncorrectFormatException | IncorrectPasswordException | NoSuchUserException | PasswordMatchingException e) {
            request.getSession().setAttribute(ERROR, e.getMessage());
        }

        return getActionToRedirect(CHANGE_PASSWORD_ACTION);
    }
}
