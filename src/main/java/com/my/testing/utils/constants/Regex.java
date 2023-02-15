package com.my.testing.utils.constants;

public final class Regex {

    public static final String EMAIL_REGEX = "^[\\w.%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$";
    public static final String PASSWORD_REGEX = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,20}$";
    public static final String NAME_REGEX = "^[A-Za-zА-ЩЬЮЯҐІЇЄа-щьюяґіїє'\\- ]{1,30}";
    public static final String TEST_NAME_REGEX = "^[A-Za-zА-ЩЬЮЯҐІЇЄа-щьюяґіїє0-9'\".,:!?\\-() ]{1,100}";
    public static final String QUESTION_TEXT_REGEX = "[\\w\\W]{1,400}";
    public static final String ANSWER_TEXT_REGEX = "[\\w\\W]{1,150}";

    private Regex() {}
}
