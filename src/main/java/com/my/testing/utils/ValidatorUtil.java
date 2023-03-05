package com.my.testing.utils;

import com.my.testing.exceptions.*;
import com.my.testing.utils.constants.Regex;
import lombok.*;

import static com.my.testing.exceptions.constants.Message.*;

/**
 * Validator to validate emails, names, etc...
 *
 * @author Khelemendyk Dmytro
 * @version 1.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ValidatorUtil {

    public static void validateEmail(String email) throws IncorrectFormatException {
        validateFormat(email, Regex.EMAIL_REGEX, ENTER_CORRECT_EMAIL);
    }

    public static void validatePassword(String password) throws  IncorrectFormatException {
        validateFormat(password, Regex.PASSWORD_REGEX, ENTER_CORRECT_PASSWORD);
    }

    public static void validateName(String name, String message) throws IncorrectFormatException {
        validateFormat(name, Regex.NAME_REGEX, message);
    }

    public static void validateTestName(String name) throws IncorrectFormatException {
        validateFormat(name, Regex.TEST_NAME_REGEX, ENTER_CORRECT_NAME);
    }

    public static void validateQuestionText(String text) throws IncorrectFormatException {
        validateFormat(text, Regex.QUESTION_TEXT_REGEX, ENTER_CORRECT_TEXT);
    }

    public static void validateAnswerText(String text) throws IncorrectFormatException {
        validateFormat(text, Regex.ANSWER_TEXT_REGEX, ENTER_CORRECT_TEXT);
    }

    public static void validateFormat(String name, String regex, String message) throws IncorrectFormatException {
        if (name == null || !name.matches(regex))
            throw new IncorrectFormatException(message);
    }

    public static long getUserId(String idString) throws ServiceException {
        return checkId(idString, new NoSuchUserException());
    }

    public static long getTestId(String idString) throws ServiceException {
        return checkId(idString, new NoSuchTestException());
    }

    public static long getQuestionId(String idString) throws ServiceException {
        return checkId(idString, new NoSuchQuestionException());
    }

    public static long getAnswerId(String idString) throws ServiceException {
        return checkId(idString, new NoSuchAnswerException());
    }

    public static long getTestResultId(String idString) throws ServiceException {
        return checkId(idString, new NoSuchTestResultException());
    }

    public static long checkId(String idString, ServiceException exception) throws ServiceException {
        long id;
        try {
            id = Long.parseLong(idString);
        } catch (NumberFormatException e) {
            throw exception;
        }
        return id;
    }

    public static void checkPasswordMatching(String password, String confirmPassword) throws PasswordMatchingException {
        if (!password.equals(confirmPassword)) {
            throw new PasswordMatchingException();
        }
    }

    public static void checkStrings(String ... strings) throws ServiceException {
        for (String string : strings) {
            if (string == null) {
                throw new ServiceException(new NullPointerException("Enter valid values"));
            }
        }
    }
}
