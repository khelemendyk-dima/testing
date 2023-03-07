package com.my.testing.exceptions;

/**
 * Main exception for all types of Web application mistakes.
 * Direct subclasses: CaptchaException, DuplicateEmailException, IncorrectFormatException, IncorrectPasswordException,
 * NoSuchAnswerException, NoSuchQuestionException, NoSuchTestException, NoSuchTestResultException, NoSuchUserException,
 * PasswordMatchingException
 *
 * @author Khelemendyk Dmytro
 * @version 1.0
 */
public class ServiceException extends Exception {

    public ServiceException(String message) {
        super(message);
    }
    public ServiceException(Throwable cause) {
        super(cause);
    }
}
