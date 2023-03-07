package com.my.testing.exceptions;

import static com.my.testing.exceptions.constants.Message.NO_ANSWER;

/**
 * In case of no such answer
 *
 * @author Khelemendyk Dmytro
 * @version 1.0
 */
public class NoSuchAnswerException extends ServiceException {
    public NoSuchAnswerException() {
        super(NO_ANSWER);
    }
}
