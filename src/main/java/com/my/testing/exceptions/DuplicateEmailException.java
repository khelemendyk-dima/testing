package com.my.testing.exceptions;

import static com.my.testing.exceptions.constants.Message.DUPLICATE_EMAIL;

/**
 * Uses to change SQLException to ServiceException
 *
 * @author Khelemendyk Dmytro
 * @version 1.0
 */
public class DuplicateEmailException extends ServiceException {
    public DuplicateEmailException() {
        super(DUPLICATE_EMAIL);
    }
}
