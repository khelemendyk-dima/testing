package com.my.testing.exceptions;

/**
 * User different messages for incorrect email, password, name, surname.
 *
 * @author Khelemendyk Dmytro
 * @version 1.0
 */
public class IncorrectFormatException extends ServiceException {
    public IncorrectFormatException(String message) {
        super(message);
    }
}
