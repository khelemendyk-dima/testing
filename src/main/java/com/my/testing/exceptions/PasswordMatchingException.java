package com.my.testing.exceptions;

import static com.my.testing.exceptions.constants.Message.PASSWORD_MATCHING;

public class PasswordMatchingException extends ServiceException {
    public PasswordMatchingException() {
        super(PASSWORD_MATCHING);
    }
}
