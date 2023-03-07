package com.my.testing.exceptions;

import static com.my.testing.exceptions.constants.Message.PASSWORD_MATCHING;

/**
 * If password doesn't match confirmation password
 *
 * @author Khelemendyk Dmytro
 * @version 1.0
 */
public class PasswordMatchingException extends ServiceException {
    public PasswordMatchingException() {
        super(PASSWORD_MATCHING);
    }
}
