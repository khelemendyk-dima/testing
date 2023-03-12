package com.my.testing.controller.actions.implementation.base;

import com.my.testing.controller.actions.Action;
import com.my.testing.controller.context.AppContext;
import com.my.testing.dto.UserDTO;
import com.my.testing.exceptions.*;
import com.my.testing.model.services.UserService;
import jakarta.servlet.http.*;
import org.apache.logging.log4j.*;

import static com.my.testing.controller.actions.ActionUtil.*;
import static com.my.testing.controller.actions.constants.ActionNames.SIGN_IN_ACTION;
import static com.my.testing.controller.actions.constants.Pages.*;
import static com.my.testing.controller.actions.constants.Parameters.*;

/**
 * This is SignInAction class. Accessible by any user. Allows to sign in web app. Implements PRG pattern
 *
 * @author Khelemendyk Dmytro
 * @version 1.0
 */
public class SignInAction implements Action {
    private static final Logger logger = LogManager.getLogger(SignInAction.class);
    private final UserService userService;

    /**
     * @param appContext contains UserService instance to user in action
     */
    public SignInAction(AppContext appContext) {
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
     * @param request to get email and error attribute from session and put it in request. Email for user to check
     * for mistakes
     * @return sign in page
     */
    private String executeGet(HttpServletRequest request) {
        transferStringFromSessionToRequest(request, EMAIL);
        transferStringFromSessionToRequest(request, ERROR);
        return SIGN_IN_PAGE;
    }

    /**
     * Called from doPost method in front-controller. Tries to sign in web app. If successful sets user to session and
     * redirects to profile page, if not sets error and email and redirects to executeGet method through front-controller
     * if not
     * @param request to get users email, password and set some attributes in session
     * @return profile page if successful or path to redirect to executeGet method through front-controller if not
     */
    private String executePost(HttpServletRequest request) throws ServiceException {
        String email = request.getParameter(EMAIL);
        String password = request.getParameter(PASSWORD);
        try {
            UserDTO user = userService.signIn(email, password);
            setLoggedUser(request, user);
            logger.info(String.format("%s entered web app", email));
            return PROFILE_PAGE;
        } catch (NoSuchUserException | IncorrectPasswordException e) {
            request.getSession().setAttribute(ERROR, e.getMessage());
            request.getSession().setAttribute(EMAIL, email);
            logger.error(String.format("%s tried to enter web app but couldn't - %s", email, e.getMessage()));
            return getActionToRedirect(SIGN_IN_ACTION);
        }
    }

    private static void setLoggedUser(HttpServletRequest request, UserDTO user) {
        request.getSession().setAttribute(LOGGED_USER, user);
        request.getSession().setAttribute(ROLE, user.getRole());
    }
}
