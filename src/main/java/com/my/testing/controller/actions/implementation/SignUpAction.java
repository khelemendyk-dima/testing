package com.my.testing.controller.actions.implementation;

import com.my.testing.controller.actions.Action;
import com.my.testing.controller.context.AppContext;
import com.my.testing.dto.UserDTO;
import com.my.testing.exceptions.*;
import com.my.testing.model.services.UserService;
import jakarta.servlet.http.HttpServletRequest;

import static com.my.testing.controller.actions.ActionUtil.*;
import static com.my.testing.controller.actions.constants.ActionNames.SIGN_UP_ACTION;
import static com.my.testing.controller.actions.constants.Pages.*;
import static com.my.testing.controller.actions.constants.ParameterValues.SUCCEED_REGISTERED;
import static com.my.testing.controller.actions.constants.Parameters.*;

public class SignUpAction implements Action {
    private final UserService userService;

    public SignUpAction(AppContext appContext) {
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
        return getPath(request);
    }

    private String executePost(HttpServletRequest request) throws ServiceException {
        String path = SIGN_IN_PAGE;
        UserDTO user = getUserDTO(request);
        request.getSession().setAttribute(USER, user);
        try {
            userService.add(user, request.getParameter(PASSWORD), request.getParameter(CONFIRM_PASSWORD));
            request.getSession().setAttribute(MESSAGE, SUCCEED_REGISTERED);
        } catch (IncorrectFormatException | PasswordMatchingException | DuplicateEmailException e) {
            request.getSession().setAttribute(ERROR, e.getMessage());
            path = SIGN_UP_PAGE;
        }
        request.getSession().setAttribute(CURRENT_PATH, path);

        return getActionToRedirect(SIGN_UP_ACTION);
    }

    private UserDTO getUserDTO(HttpServletRequest request) {
        return UserDTO.builder()
                .email(request.getParameter(EMAIL))
                .name(request.getParameter(NAME))
                .surname(request.getParameter(SURNAME))
                .build();
    }
}
