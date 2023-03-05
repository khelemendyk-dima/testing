package com.my.testing.utils.constants;

import lombok.*;

/**
 * Contains all required for validation regexes
 *
 * @author Khelemendyk Dmytro
 * @version 1.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Regex {
    /** Use it for email only */
    public static final String EMAIL_REGEX = "^[\\w.%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$";
    /** Use it for password only */
    public static final String PASSWORD_REGEX = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,20}$";
    /** Use it for names and surnames */
    public static final String NAME_REGEX = "^[A-Za-zА-ЩЬЮЯҐІЇЄа-щьюяґіїє'\\- ]{1,30}";
    /** Use it for test name only */
    public static final String TEST_NAME_REGEX = "^[A-Za-zА-ЩЬЮЯҐІЇЄа-щьюяґіїє0-9'\".,:!?\\-() ]{1,100}";
    /** Use it for question text only */
    public static final String QUESTION_TEXT_REGEX = "[\\w\\W]{1,400}";
    /** Use it for answer text only */
    public static final String ANSWER_TEXT_REGEX = "[\\w\\W]{1,150}";
}
