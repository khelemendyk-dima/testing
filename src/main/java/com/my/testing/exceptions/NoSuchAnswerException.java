package com.my.testing.exceptions;

import static com.my.testing.exceptions.constants.Message.NO_ANSWER;

public class NoSuchAnswerException extends ServiceException {
    public NoSuchAnswerException() {
        super(NO_ANSWER);
    }
}
