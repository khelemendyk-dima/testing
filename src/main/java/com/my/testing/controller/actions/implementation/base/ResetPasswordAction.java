package com.my.testing.controller.actions.implementation.base;

import com.my.testing.controller.actions.Action;
import com.my.testing.controller.context.AppContext;
import com.my.testing.dto.UserDTO;
import com.my.testing.exceptions.ServiceException;
import com.my.testing.model.services.UserService;
import com.my.testing.utils.EmailSenderUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.my.testing.controller.actions.ActionUtil.*;
import static com.my.testing.controller.actions.constants.ActionNames.RESET_PASSWORD_ACTION;
import static com.my.testing.controller.actions.constants.Pages.RESET_PASSWORD_PAGE;
import static com.my.testing.controller.actions.constants.ParameterValues.CHECK_EMAIL;
import static com.my.testing.controller.actions.constants.Parameters.*;
import static com.my.testing.utils.constants.Email.MESSAGE_RESET_PASSWORD;
import static com.my.testing.utils.constants.Email.SUBJECT_NOTIFICATION;

public class ResetPasswordAction implements Action {
    private static final Logger logger = LogManager.getLogger(ResetPasswordAction.class);
    private final UserService userService;
    private final EmailSenderUtil emailSender;

    public ResetPasswordAction(AppContext appContext) {
        userService = appContext.getUserService();
        emailSender = appContext.getEmailSender();
    }
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        return isPostMethod(request) ? executePost(request) : executeGet(request);
    }

    private String executeGet(HttpServletRequest request) {
        transferStringFromSessionToRequest(request, EMAIL);
        transferStringFromSessionToRequest(request, ERROR);
        transferStringFromSessionToRequest(request, MESSAGE);
        return RESET_PASSWORD_PAGE;
    }

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
