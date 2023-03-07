package com.my.testing.exceptions.constants;

import lombok.*;

/**
 * Contains messages for all user-defined exceptions
 *
 * @author Khelemendyk Dmytro
 * @version 1.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Message {

    public static final String ENTER_CORRECT_EMAIL = "error.email.format";
    public static final String ENTER_CORRECT_NAME = "error.name.format";
    public static final String ENTER_CORRECT_SURNAME = "error.surname.format";
    public static final String ENTER_CORRECT_TEXT = "error.text.format";
    public static final String ENTER_CORRECT_PASSWORD = "error.password.format";
    public static final String DUPLICATE_EMAIL = "error.email.duplicate";
    public static final String PASSWORD_MATCHING = "error.password.match";
    public static final String WRONG_PASSWORD = "error.password.wrong";
    public static final String FEW_ANSWERS = "error.answer.few";
    public static final String NO_USER = "error.email.absent";
    public static final String NO_TEST = "error.test.absent";
    public static final String NO_TEST_RESULT = "error.test.result.absent";
    public static final String NO_QUESTION = "error.question.absent";
    public static final String NO_ANSWER = "error.answer.absent";
    public static final String NO_CORRECT_ANSWER = "error.answer.no.correct";
    public static final String CAPTCHA_INVALID = "error.captcha.invalid";
}
