package com.my.testing.exceptions;

import static com.my.testing.exceptions.constants.Message.NO_TEST_RESULT;

/**
 * In case of no such test result
 *
 * @author Khelemendyk Dmytro
 * @version 1.0
 */
public class NoSuchTestResultException extends ServiceException {
    public NoSuchTestResultException() {
        super(NO_TEST_RESULT);
    }
}
