package com.my.testing.exceptions;

import static com.my.testing.exceptions.constants.Message.NO_TEST_RESULT;

public class NoSuchTestResultException extends ServiceException {
    public NoSuchTestResultException() {
        super(NO_TEST_RESULT);
    }
}
