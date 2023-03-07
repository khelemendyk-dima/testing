package com.my.testing.exceptions;

import static com.my.testing.exceptions.constants.Message.CAPTCHA_INVALID;

/**
 * For not passing captcha exception
 *
 * @author Khelemendyk Dmytro
 * @version 1.0
 */
public class CaptchaException extends ServiceException {
    public CaptchaException() {
        super(CAPTCHA_INVALID);
    }
}
