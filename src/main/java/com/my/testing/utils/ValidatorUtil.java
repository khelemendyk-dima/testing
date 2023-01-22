package com.my.testing.utils;

import com.my.testing.exceptions.*;
import com.my.testing.utils.constants.Regex;

import static com.my.testing.exceptions.constants.Message.*;

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

    public static void validateFormat(String name, String regex, String message) throws IncorrectFormatException {
        if (name == null || !name.matches(regex))
            throw new IncorrectFormatException(message);
    }

    public static long getUserId(String idString) throws ServiceException {
        return checkId(idString, new NoSuchUserException());
    }

    public static long checkId(String idString, ServiceException exception) throws ServiceException {
        long eventId;
        try {
            eventId = Long.parseLong(idString);
        } catch (NumberFormatException e) {
            throw exception;
        }
        return eventId;
    }

    public static void checkPasswordMatching(String password, String confirmPassword) throws PasswordMatchingException {
        if (!password.equals(confirmPassword)) {
            throw new PasswordMatchingException();
        }
    }

    public static void checkStrings(String ... strings) throws ServiceException {
        for (String string : strings) {
            if (string == null) {
                throw new ServiceException(new NullPointerException());
            }
        }
    }

    private ValidatorUtil() {}
}
