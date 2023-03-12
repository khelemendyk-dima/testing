package com.my.testing.controller.actions.implementation.base;

import com.my.testing.controller.actions.Action;
import com.my.testing.dto.UserDTO;
import jakarta.servlet.http.*;
import org.apache.logging.log4j.*;

import static com.my.testing.controller.actions.constants.Pages.SIGN_IN_PAGE;
import static com.my.testing.controller.actions.constants.Parameters.*;

/**
 * This is SignOutAction class. Accessible by any logged user. Allows to sign out of web app.
 *
 * @author Khelemendyk Dmytro
 * @version 1.0
 */
public class SignOutAction implements Action {
    private static final Logger logger = LogManager.getLogger(SignOutAction.class);

    /**
     * Invalidates session. Saves locale and sets to new session so language will not change for user
     * @param request passed to get session
     * @return sign in page
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        UserDTO user = (UserDTO) session.getAttribute(LOGGED_USER);

        if (user != null) {
            String locale = (String) session.getAttribute(LOCALE);
            session.invalidate();
            logger.info(String.format("%s signed out", user.getEmail()));
            request.getSession(true).setAttribute(LOCALE, locale);
        }

        return SIGN_IN_PAGE;
    }
}
