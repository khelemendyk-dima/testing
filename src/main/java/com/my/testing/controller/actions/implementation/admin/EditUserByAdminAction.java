package com.my.testing.controller.actions.implementation.admin;

import com.my.testing.controller.actions.Action;
import com.my.testing.controller.context.AppContext;
import com.my.testing.dto.UserDTO;
import com.my.testing.exceptions.*;
import com.my.testing.model.services.UserService;
import jakarta.servlet.http.*;
import org.apache.logging.log4j.*;

import static com.my.testing.controller.actions.ActionUtil.*;
import static com.my.testing.controller.actions.constants.ActionNames.EDIT_USER_BY_ADMIN_ACTION;
import static com.my.testing.controller.actions.constants.Pages.EDIT_USER_BY_ADMIN_PAGE;
import static com.my.testing.controller.actions.constants.ParameterValues.SUCCEED_UPDATED;
import static com.my.testing.controller.actions.constants.Parameters.*;

/**
 * This is EditUserByAdminAction class. Accessible by admin. Allows to edit user's profile by admin.
 * Implements PRG pattern
 *
 * @author Khelemendyk Dmytro
 * @version 1.0
 */
public class EditUserByAdminAction implements Action {
    private static final Logger logger = LogManager.getLogger(EditUserByAdminAction.class);
    private final UserService userService;

    /**
     * @param appContext contains UserService instance to use in action
     */
    public EditUserByAdminAction(AppContext appContext) {
        userService = appContext.getUserService();
    }

    /**
     * Checks method and calls required implementation
     * @param request to get method, session and set all required attributes
     * @return path to redirect or forward by front-controller
     * @throws ServiceException to call error page in front-controller* @param response passed by controller
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        return isPostMethod(request) ? executePost(request) : executeGet(request);
    }

    /**
     * Called from doGet method in front-controller. Obtains required path and transfer attributes from session
     * to request.
     * @param request to get user, message and error attributes from session and put it in request
     * @return edit user by admin page
     */
    private String executeGet(HttpServletRequest request) {
        transferStringFromSessionToRequest(request, MESSAGE);
        transferStringFromSessionToRequest(request, ERROR);
        transferUserDTOFromSessionToRequest(request);
        return EDIT_USER_BY_ADMIN_PAGE;
    }

    /**
     * Called from doPost method in front-controller. Gets user from request. Then updating user in database
     * @param request to get user and set some attributes to session
     * @return path to redirect to executeGet method through front-controller with required parameters
     */
    private String executePost(HttpServletRequest request) throws ServiceException {
        UserDTO user = getUserDTO(request);
        request.getSession().setAttribute(USER, user);

        try {
            userService.update(user);
            request.getSession().setAttribute(MESSAGE, SUCCEED_UPDATED);
            logger.info(String.format("User %s was successfully changed", user.getEmail()));
        } catch (IncorrectFormatException | DuplicateEmailException e) {
            request.getSession().setAttribute(ERROR, e.getMessage());
            logger.error(String.format("Couldn't change user %s because of %s", user.getEmail(), e.getMessage()));
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
