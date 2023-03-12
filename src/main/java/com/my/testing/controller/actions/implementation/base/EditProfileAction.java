package com.my.testing.controller.actions.implementation.base;

import com.my.testing.controller.actions.Action;
import com.my.testing.controller.context.AppContext;
import com.my.testing.dto.UserDTO;
import com.my.testing.exceptions.*;
import com.my.testing.model.services.UserService;
import jakarta.servlet.http.*;
import org.apache.logging.log4j.*;

import static com.my.testing.controller.actions.ActionUtil.*;
import static com.my.testing.controller.actions.constants.ActionNames.EDIT_PROFILE_ACTION;
import static com.my.testing.controller.actions.constants.Pages.EDIT_PROFILE_PAGE;
import static com.my.testing.controller.actions.constants.ParameterValues.SUCCEED_UPDATED;
import static com.my.testing.controller.actions.constants.Parameters.*;

/**
 * This is EditProfileAction class. Accessible by any logged user. Allows to change user's email, name and surname.
 * Implements PRG pattern
 *
 * @author Khelemendyk Dmytro
 * @version 1.0
 */
public class EditProfileAction implements Action {
    private static final Logger logger = LogManager.getLogger(EditProfileAction.class);
    private final UserService userService;

    /**
     * @param appContext contains UserService instance to use in action
     */
    public EditProfileAction(AppContext appContext) {
        userService = appContext.getUserService();
    }

    /**
     * Checks method and calls required implementation
     * @param request to get method, session and set all required attributes
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
     * @param request to get message, UserDTO or error attribute from session and put it in request
     * @return edit profile page
     */
    private String executeGet(HttpServletRequest request) {
        transferStringFromSessionToRequest(request, MESSAGE);
        transferStringFromSessionToRequest(request, ERROR);
        transferUserDTOFromSessionToRequest(request);
        return EDIT_PROFILE_PAGE;
    }

    /**
     * Called from doPost method in front-controller. Tries to change user's email, name, password via service.
     * Sets UserDTO to session so user will not need to enter fields again. Update logged user in session if
     * editing was successful
     * @param request to get user's id and all required fields. Also, to set message in case of successful deleting and
     * error in another case
     * @return path to redirect to executeGet method through front-controller
     * @throws ServiceException to set error message in session and user for input values
     */
    private String executePost(HttpServletRequest request) throws ServiceException {
        UserDTO sessionUser = (UserDTO) request.getSession().getAttribute(LOGGED_USER);
        UserDTO user = getUserDTO(request, sessionUser);
        try {
            userService.update(user);
            request.getSession().setAttribute(MESSAGE, SUCCEED_UPDATED);
            logger.info(String.format("%s was successfully changed his profile info", sessionUser.getEmail()));
            updateSessionUser(sessionUser, user);
        } catch (IncorrectFormatException | DuplicateEmailException e) {
            request.getSession().setAttribute(USER, user);
            request.getSession().setAttribute(ERROR, e.getMessage());
            logger.info(String.format("%s couldn't change his profile info", sessionUser.getEmail()));
        }

        return getActionToRedirect(EDIT_PROFILE_ACTION);
    }

    private UserDTO getUserDTO(HttpServletRequest request, UserDTO currentUser) {
        return UserDTO.builder()
                .id(currentUser.getId())
                .email(request.getParameter(EMAIL))
                .name(request.getParameter(NAME))
                .surname(request.getParameter(SURNAME))
                .build();
    }

    private void updateSessionUser(UserDTO currentUser, UserDTO user) {
        currentUser.setEmail(user.getEmail());
        currentUser.setName(user.getName());
        currentUser.setSurname(user.getSurname());
    }
}
