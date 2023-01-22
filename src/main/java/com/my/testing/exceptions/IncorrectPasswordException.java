package com.my.testing.exceptions;

import static com.my.testing.exceptions.constants.Message.WRONG_PASSWORD;

public class IncorrectPasswordException extends ServiceException {
    public IncorrectPasswordException() {
        super(WRONG_PASSWORD);
    }
}
