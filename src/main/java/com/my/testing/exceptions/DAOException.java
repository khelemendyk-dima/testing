package com.my.testing.exceptions;

/**
 * Wrapper for SQLException
 *
 * @author Khelemendyk Dmytro
 * @version 1.0
 */
public class DAOException extends Exception {
    public DAOException(Throwable cause) {
        super(cause);
    }
}
