package com.my.testing.exceptions;

import static com.my.testing.exceptions.constants.Message.NO_USER;

public class NoSuchUserException extends ServiceException {
    public NoSuchUserException() {
        super(NO_USER);
    }
}
