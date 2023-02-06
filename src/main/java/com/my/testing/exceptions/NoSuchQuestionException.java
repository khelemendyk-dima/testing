package com.my.testing.exceptions;

import static com.my.testing.exceptions.constants.Message.NO_QUESTION;

public class NoSuchQuestionException extends ServiceException {
    public NoSuchQuestionException() {
        super(NO_QUESTION);
    }
}
