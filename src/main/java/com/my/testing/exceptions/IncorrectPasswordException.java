package com.my.testing.exceptions;

import static com.my.testing.exceptions.constants.Message.WRONG_PASSWORD;

/**
 * If password does not match database password
 *
 * @author Khelemendyk Dmytro
 * @version 1.0
 */
public class IncorrectPasswordException extends ServiceException {
    public IncorrectPasswordException() {
        super(WRONG_PASSWORD);
    }
}
