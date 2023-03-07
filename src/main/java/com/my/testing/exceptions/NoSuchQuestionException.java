package com.my.testing.exceptions;

import static com.my.testing.exceptions.constants.Message.NO_QUESTION;

/**
 * In case of no such question
 *
 * @author Khelemendyk Dmytro
 * @version 1.0
 */
public class NoSuchQuestionException extends ServiceException {
    public NoSuchQuestionException() {
        super(NO_QUESTION);
    }
}
