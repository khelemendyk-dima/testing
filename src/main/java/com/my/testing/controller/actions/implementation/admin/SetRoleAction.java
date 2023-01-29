package com.my.testing.controller.actions.implementation.admin;

import com.my.testing.controller.actions.Action;
import com.my.testing.controller.context.AppContext;
import com.my.testing.exceptions.ServiceException;
import com.my.testing.model.entities.enums.Role;
import com.my.testing.model.services.UserService;
import jakarta.servlet.http.HttpServletRequest;

import static com.my.testing.controller.actions.ActionUtil.getActionToRedirect;
import static com.my.testing.controller.actions.constants.ActionNames.FIND_USER_ACTION;
import static com.my.testing.controller.actions.constants.Parameters.*;

public class SetRoleAction implements Action {
    private final UserService userService;
    public SetRoleAction(AppContext appContext) {
        userService = appContext.getUserService();
    }

    @Override
    public String execute(HttpServletRequest request) throws ServiceException {
        String email = request.getParameter(EMAIL);
        int roleId = Role.valueOf(request.getParameter(ROLE)).getValue();
        userService.setRole(email, roleId);
        request.setAttribute(USER, userService.getByEmail(email));
        return getActionToRedirect(FIND_USER_ACTION, EMAIL, email);
    }
}
