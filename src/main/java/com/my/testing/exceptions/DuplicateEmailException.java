package com.my.testing.exceptions;

import static com.my.testing.exceptions.constants.Message.DUPLICATE_EMAIL;

public class DuplicateEmailException extends ServiceException {
    public DuplicateEmailException() {
        super(DUPLICATE_EMAIL);
    }
}
