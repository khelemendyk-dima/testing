package com.my.testing.controller.actions.implementation.base;

import com.my.testing.controller.actions.Action;
import com.my.testing.controller.context.AppContext;
import com.my.testing.dto.UserDTO;
import com.my.testing.exceptions.*;
import com.my.testing.model.services.UserService;
import jakarta.servlet.http.*;
import org.apache.logging.log4j.*;

import static com.my.testing.controller.actions.ActionUtil.*;
import static com.my.testing.controller.actions.constants.Pages.CHANGE_PASSWORD_PAGE;
import static com.my.testing.controller.actions.constants.ParameterValues.SUCCEED_UPDATED;
import static com.my.testing.controller.actions.constants.Parameters.*;
import static com.my.testing.controller.actions.constants.ActionNames.CHANGE_PASSWORD_ACTION;

/**
 * This is ChangePasswordAction class. Accessible by any logged user. Allows to change user's password.
 * Implements PRG pattern
 *
 * @author Khelemendyk Dmytro
 * @version 1.0
 */
public class ChangePasswordAction implements Action {
    private static final Logger logger = LogManager.getLogger(ChangePasswordAction.class);
    private final UserService userService;

    /**
     * @param appContext contains UseService instance to use in action
     */
    public ChangePasswordAction(AppContext appContext) {
        userService = appContext.getUserService();
    }

    /**
     * Checks method and calls required implementation
     * @param request to get method, session and set all required attributes
     * @param response passed by controller
     * @return path to redirect or forward by front-controller
     * @throws ServiceException to call error page in front-controller
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        return isPostMethod(request) ? executePost(request) : executeGet(request);
    }

    /**
     * Called from doGet method in front-controller. Obtains required path and transfer attributes from session
     * to request
     * @param request to get message or error attribute from session and put it in request
     * @return change password page
     */
    private String executeGet(HttpServletRequest request) {
        transferStringFromSessionToRequest(request, MESSAGE);
        transferStringFromSessionToRequest(request, ERROR);
        return CHANGE_PASSWORD_PAGE;
    }

    /**
     * Called from doPost method in front-controller. Tries to change users password via service
     * @param request to get users id and oll passwords. Also, to set message in case of successful deleting and error
     * in other case
     * @return path to redirect to execute Get method through front-controller
     * @throws ServiceException to set error message in session
     */
    private String executePost(HttpServletRequest request) throws ServiceException {
        UserDTO userDTO = ((UserDTO) request.getSession().getAttribute(LOGGED_USER));

        try {
            userServiceChangePassword(request, userDTO);
            request.getSession().setAttribute(MESSAGE, SUCCEED_UPDATED);
        } catch (IncorrectFormatException | IncorrectPasswordException | NoSuchUserException | PasswordMatchingException e) {
            request.getSession().setAttribute(ERROR, e.getMessage());
            logger.error(String.format("%s couldn't change password", userDTO.getEmail()));
        }

        return getActionToRedirect(CHANGE_PASSWORD_ACTION);
    }

    /**
     * Gets old, new and confirm passwords from request and tries to change user's password via service
     * @param request to get passwords
     * @param userDTO to get id and email
     * @throws ServiceException if something go wrong in changing password
     */
    private void userServiceChangePassword(HttpServletRequest request, UserDTO userDTO) throws ServiceException {
        long id = userDTO.getId();
        String oldPassword = request.getParameter(OLD_PASSWORD);
        String password = request.getParameter(PASSWORD);
        String confirmPassword = request.getParameter(CONFIRM_PASSWORD);
        userService.changePassword(id, oldPassword, password, confirmPassword);
        logger.info(String.format("%s changed his password", userDTO.getEmail()));
    }
}
