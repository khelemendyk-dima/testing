package com.my.testing.exceptions;

import static com.my.testing.exceptions.constants.Message.NO_USER;

/**
 * In case of no such user
 *
 * @author Khelemendyk Dmytro
 * @version 1.0
 */
public class NoSuchUserException extends ServiceException {
    public NoSuchUserException() {
        super(NO_USER);
    }
}
