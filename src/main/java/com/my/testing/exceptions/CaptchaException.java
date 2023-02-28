package com.my.testing.exceptions;

import static com.my.testing.exceptions.constants.Message.CAPTCHA_INVALID;

public class CaptchaException extends ServiceException {
    public CaptchaException() {
        super(CAPTCHA_INVALID);
    }
}
