package com.my.testing.controller.actions.implementation.admin;

import com.my.testing.controller.actions.Action;
import com.my.testing.controller.context.AppContext;
import com.my.testing.exceptions.ServiceException;
import com.my.testing.model.entities.enums.Role;
import com.my.testing.model.services.UserService;
import jakarta.servlet.http.*;
import org.apache.logging.log4j.*;

import static com.my.testing.controller.actions.ActionUtil.getActionToRedirect;
import static com.my.testing.controller.actions.constants.ActionNames.FIND_USER_ACTION;
import static com.my.testing.controller.actions.constants.Parameters.*;

/**
 * This is SetRoleAction class. Accessible by admin. Allows to set user's role.
 *
 * @author Khelemendyk Dmytro
 * @version 1.0
 */
public class SetRoleAction implements Action {
    private static final Logger logger = LogManager.getLogger(SetRoleAction.class);
    private final UserService userService;

    /**
     * @param appContext contains UserService to use in action
     */
    public SetRoleAction(AppContext appContext) {
        userService = appContext.getUserService();
    }

    /**
     * Obtains required path and sets user's role
     * @param request to get user's email, new role and put user in request
     * @return path to redirect to execute method in FindUserAction through
     * front-controller with required parameters to find user
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        String email = request.getParameter(EMAIL);
        String role = request.getParameter(ROLE);
        int roleId = Role.valueOf(role).getValue();
        userService.setRole(email, roleId);
        logger.info(String.format("User %s got new role: %s", email, role));
        request.setAttribute(USER, userService.getByEmail(email));
        return getActionToRedirect(FIND_USER_ACTION, EMAIL, email);
    }
}
