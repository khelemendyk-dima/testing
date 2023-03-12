package com.my.testing.controller.actions.implementation.admin;

import com.my.testing.controller.actions.Action;
import com.my.testing.controller.context.AppContext;
import com.my.testing.exceptions.*;
import com.my.testing.model.services.UserService;
import jakarta.servlet.http.*;
import org.apache.logging.log4j.*;

import static com.my.testing.controller.actions.constants.Pages.*;
import static com.my.testing.controller.actions.constants.Parameters.*;

/**
 * This is FindUserAction class. Accessible by admin. Allows to find user from database by email
 *
 * @author Khelemendyk Dmytro
 * @version 1.0
 */
public class FindUserAction implements Action {
    private static final Logger logger = LogManager.getLogger(FindUserAction.class);
    private final UserService userService;

    /**
     * @param appContext contains UserService instance to use in action
     */
    public FindUserAction(AppContext appContext) {
        userService = appContext.getUserService();
    }

    /**
     * Obtains required path and sets user to request if it was found
     * @param request to get user's email and put user in request or error if it can't find user
     * @return user by email page if it was found, else - find user page
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        try {
            request.setAttribute(USER, userService.getByEmail(request.getParameter(EMAIL)));
            return USER_INFO_BY_EMAIL_PAGE;
        } catch (NoSuchUserException | IncorrectFormatException e) {
            logger.info(String.format("Couldn't find user because of %s", e.getMessage()));
            request.setAttribute(ERROR, e.getMessage());
            request.setAttribute(EMAIL, request.getParameter(EMAIL));
            return FIND_USER_PAGE;
        }
    }
}
