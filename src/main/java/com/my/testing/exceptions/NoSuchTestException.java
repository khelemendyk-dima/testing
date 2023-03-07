package com.my.testing.exceptions;

import static com.my.testing.exceptions.constants.Message.NO_TEST;

/**
 * In case of no such test
 *
 * @author Khelemendyk Dmytro
 * @version 1.0
 */
public class NoSuchTestException extends ServiceException {
    public NoSuchTestException() {
        super(NO_TEST);
    }
}
