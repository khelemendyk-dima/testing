package com.my.testing.controller.actions.implementation.base;

import com.my.testing.controller.actions.Action;
import com.my.testing.controller.context.AppContext;
import com.my.testing.dto.UserDTO;
import com.my.testing.exceptions.ServiceException;
import com.my.testing.model.services.UserService;
import com.my.testing.utils.EmailSenderUtil;
import jakarta.servlet.http.*;
import org.apache.logging.log4j.*;

import static com.my.testing.controller.actions.ActionUtil.*;
import static com.my.testing.controller.actions.constants.ActionNames.RESET_PASSWORD_ACTION;
import static com.my.testing.controller.actions.constants.Pages.RESET_PASSWORD_PAGE;
import static com.my.testing.controller.actions.constants.ParameterValues.CHECK_EMAIL;
import static com.my.testing.controller.actions.constants.Parameters.*;
import static com.my.testing.utils.constants.Email.*;

/**
 * This is ResetPasswordAction class. Accessible by any user. Allows to reset user's password.
 * Implements PRG pattern
 *
 * @author Khelemendyk Dmytro
 * @version 1.0
 */
public class ResetPasswordAction implements Action {
    private static final Logger logger = LogManager.getLogger(ResetPasswordAction.class);
    private final UserService userService;
    private final EmailSenderUtil emailSender;

    /**
     * @param appContext contains UserService and EmailSender instances to use in action
     */
    public ResetPasswordAction(AppContext appContext) {
        userService = appContext.getUserService();
        emailSender = appContext.getEmailSender();
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
     * @param request to get message, email and/or error attribute from session and put it in request
     * @return reset password page
     */
    private String executeGet(HttpServletRequest request) {
        transferStringFromSessionToRequest(request, EMAIL);
        transferStringFromSessionToRequest(request, ERROR);
        transferStringFromSessionToRequest(request, MESSAGE);
        return RESET_PASSWORD_PAGE;
    }

    /**
     * Called from doGet method in front-controller. Tries to reset user's password via service. Gets email from request.
     * Sends email to user with new password if reset was successful
     * @param request to get user's id and all passwords. Also, to set message in case of successful deleting and error
     * in another case
     * @return path to redirect to executeGet method through front-controller
     */
    private String executePost(HttpServletRequest request) {
        String email = request.getParameter(EMAIL);
        request.getSession().setAttribute(EMAIL, email);
        try {
            UserDTO user = userService.getByEmail(email);
            String newPass = userService.resetPassword(user.getId());
            request.getSession().setAttribute(MESSAGE, CHECK_EMAIL);
            sendEmail(user, newPass);
            logger.info(String.format("%s was successfully reset his password", email));
        } catch (ServiceException e) {
            request.getSession().setAttribute(ERROR, e.getMessage());
            logger.info(String.format("%s couldn't reset his password", email));
        }
        return getActionToRedirect(RESET_PASSWORD_ACTION);
    }

    private void sendEmail(UserDTO user, String newPass) {
        String messageBody = String.format(MESSAGE_RESET_PASSWORD, user.getName(), newPass, user.getName(), newPass);
        new Thread(() -> emailSender.send(SUBJECT_NOTIFICATION, messageBody, user.getEmail())).start();
    }
}
