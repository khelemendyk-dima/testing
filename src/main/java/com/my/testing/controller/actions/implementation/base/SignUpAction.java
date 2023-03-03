package com.my.testing.controller.actions.implementation.base;

import com.my.testing.controller.actions.Action;
import com.my.testing.controller.context.AppContext;
import com.my.testing.dto.UserDTO;
import com.my.testing.exceptions.*;
import com.my.testing.model.services.UserService;
import com.my.testing.utils.CaptchaUtil;
import com.my.testing.utils.EmailSenderUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static com.my.testing.controller.actions.ActionUtil.*;
import static com.my.testing.controller.actions.constants.ActionNames.SIGN_UP_ACTION;
import static com.my.testing.controller.actions.constants.Pages.*;
import static com.my.testing.controller.actions.constants.ParameterValues.SUCCEED_REGISTERED;
import static com.my.testing.controller.actions.constants.Parameters.*;
import static com.my.testing.utils.constants.Email.MESSAGE_GREETINGS;
import static com.my.testing.utils.constants.Email.SUBJECT_GREETINGS;

public class SignUpAction implements Action {
    private final UserService userService;
    private final CaptchaUtil captcha;
    private final EmailSenderUtil emailSender;

    public SignUpAction(AppContext appContext) {
        userService = appContext.getUserService();
        captcha = appContext.getCaptcha();
        emailSender = appContext.getEmailSender();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        return isPostMethod(request) ? executePost(request) : executeGet(request);
    }

    private String executeGet(HttpServletRequest request) {
        transferStringFromSessionToRequest(request, MESSAGE);
        transferStringFromSessionToRequest(request, ERROR);
        transferUserDTOFromSessionToRequest(request);
        return getPath(request);
    }

    private String executePost(HttpServletRequest request) throws ServiceException {
        String path = SIGN_IN_PAGE;
        UserDTO user = getUserDTO(request);
        try {
            captcha.verify(request.getParameter(CAPTCHA));
            userService.add(user, request.getParameter(PASSWORD), request.getParameter(CONFIRM_PASSWORD));
            request.getSession().setAttribute(MESSAGE, SUCCEED_REGISTERED);
            sendEmail(user);
        } catch (IncorrectFormatException | PasswordMatchingException | DuplicateEmailException | CaptchaException e) {
            request.getSession().setAttribute(USER, user);
            request.getSession().setAttribute(ERROR, e.getMessage());
            path = SIGN_UP_PAGE;
        }
        request.getSession().setAttribute(CURRENT_PATH, path);

        return getActionToRedirect(SIGN_UP_ACTION);
    }

    private void sendEmail(UserDTO user) {
        String messageBody = String.format(MESSAGE_GREETINGS, user.getName(), user.getName());
        new Thread(() -> emailSender.send(SUBJECT_GREETINGS, messageBody, user.getEmail())).start();
    }

    private UserDTO getUserDTO(HttpServletRequest request) {
        return UserDTO.builder()
                .email(request.getParameter(EMAIL))
                .name(request.getParameter(NAME))
                .surname(request.getParameter(SURNAME))
                .build();
    }
}
