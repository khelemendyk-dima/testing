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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.my.testing.controller.actions.ActionUtil.*;
import static com.my.testing.controller.actions.constants.ActionNames.SIGN_IN_ACTION;
import static com.my.testing.controller.actions.constants.ActionNames.SIGN_UP_ACTION;
import static com.my.testing.controller.actions.constants.Pages.*;
import static com.my.testing.controller.actions.constants.ParameterValues.SUCCEED_REGISTERED;
import static com.my.testing.controller.actions.constants.Parameters.*;
import static com.my.testing.utils.constants.Email.MESSAGE_GREETINGS;
import static com.my.testing.utils.constants.Email.SUBJECT_GREETINGS;

/**
 * This is SignUpAction class. Accessible by any user. Allows to create account. Implements PRG pattern
 *
 * @author Khelemendyk Dmytro
 * @version 1.0
 */
public class SignUpAction implements Action {
    private static final Logger logger = LogManager.getLogger(SignUpAction.class);
    private final UserService userService;
    private final CaptchaUtil captcha;
    private final EmailSenderUtil emailSender;

    /**
     * @param appContext contains UserService, CaptchaUtil and EmailSender instances to use in action
     */
    public SignUpAction(AppContext appContext) {
        userService = appContext.getUserService();
        captcha = appContext.getCaptcha();
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
     * @param request to get UserDTO, message or error attribute from session and put it in request.
     * @return sign up page
     */
    private String executeGet(HttpServletRequest request) {
        transferStringFromSessionToRequest(request, MESSAGE);
        transferStringFromSessionToRequest(request, ERROR);
        transferUserDTOFromSessionToRequest(request);
        return SIGN_UP_PAGE;
    }

    /**
     * Called from doPost method in front-controller. Checks captcha. Sends email if registration was successful
     * @param request to get users fields from parameters
     * @return path to redirect to executeGet method through front-controller
     */
    private String executePost(HttpServletRequest request) throws ServiceException {
        UserDTO user = getUserDTO(request);
        try {
            captcha.verify(request.getParameter(CAPTCHA));
            userService.add(user, request.getParameter(PASSWORD), request.getParameter(CONFIRM_PASSWORD));
            request.getSession().setAttribute(MESSAGE, SUCCEED_REGISTERED);
            sendEmail(user);
            logger.info(String.format("New user registered - %s", user.getEmail()));
            return getActionToRedirect(SIGN_IN_ACTION);
        } catch (IncorrectFormatException | PasswordMatchingException | DuplicateEmailException | CaptchaException e) {
            request.getSession().setAttribute(USER, user);
            request.getSession().setAttribute(ERROR, e.getMessage());
            logger.error(String.format("New user couldn't register - %s because of %s", user.getEmail(), e.getMessage()));
            return getActionToRedirect(SIGN_UP_ACTION);
        }
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
