package com.my.testing.controller.actions.implementation.admin;

import com.my.testing.controller.actions.Action;
import com.my.testing.controller.context.AppContext;
import com.my.testing.dto.UserDTO;
import com.my.testing.exceptions.*;
import com.my.testing.model.services.UserService;
import jakarta.servlet.http.HttpServletRequest;

import static com.my.testing.controller.actions.ActionUtil.*;
import static com.my.testing.controller.actions.constants.ActionNames.EDIT_USER_BY_ADMIN_ACTION;
import static com.my.testing.controller.actions.constants.Pages.EDIT_USER_BY_ADMIN_PAGE;
import static com.my.testing.controller.actions.constants.ParameterValues.SUCCEED_UPDATE;
import static com.my.testing.controller.actions.constants.Parameters.*;

public class EditUserByAdminAction implements Action {
    private final UserService userService;

    public EditUserByAdminAction(AppContext appContext) {
        userService = appContext.getUserService();
    }
    @Override
    public String execute(HttpServletRequest request) throws ServiceException {
        return isPostMethod(request) ? executePost(request) : executeGet(request);
    }

    private String executeGet(HttpServletRequest request) {
        transferStringFromSessionToRequest(request, MESSAGE);
        transferStringFromSessionToRequest(request, ERROR);
        transferUserDTOFromSessionToRequest(request);
        return EDIT_USER_BY_ADMIN_PAGE;
    }

    private String executePost(HttpServletRequest request) throws ServiceException {
        UserDTO user = getUserDTO(request);
        try {
            userService.update(user);
            request.getSession().setAttribute(USER, user);
            request.getSession().setAttribute(MESSAGE, SUCCEED_UPDATE);
        } catch (IncorrectFormatException | DuplicateEmailException e) {
            request.getSession().setAttribute(USER, user);
            request.getSession().setAttribute(ERROR, e.getMessage());
        }

        return getActionToRedirect(EDIT_USER_BY_ADMIN_ACTION);
    }

    private UserDTO getUserDTO(HttpServletRequest request) {
        return UserDTO.builder()
                .id(Long.parseLong(request.getParameter(ID)))
                .email(request.getParameter(EMAIL))
                .name(request.getParameter(NAME))
                .surname(request.getParameter(SURNAME))
                .build();
    }
}
