package com.my.testing.exceptions;

import static com.my.testing.exceptions.constants.Message.NO_TEST;

public class NoSuchTestException extends ServiceException {
    public NoSuchTestException() {
        super(NO_TEST);
    }
}
