package com.my.testing.controller.actions.constants;

import lombok.*;

/**
 * Parameters class. It contains required parameters and attributes names
 *
 * @author Khelemendyk Dmytro
 * @version 1.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Parameters {

    /** To store logged user in session */
    public static final String LOGGED_USER = "loggedUser";

    /** Common fields */
    public static final String ID = "id";

    /** Parameters and attributes to work with UserDTO */
    public static final String USER = "user";
    public static final String ROLE = "role";
    public static final String EMAIL = "email";
    public static final String NAME = "name";
    public static final String SURNAME = "surname";
    public static final String PASSWORD = "password";
    public static final String CONFIRM_PASSWORD = "confirm-password";
    public static final String OLD_PASSWORD = "old-password";

    /** Parameters and attributes to work with TestDTO */
    public static final String TEST_ID = "testId";
    public static final String TEST = "test";
    public static final String TESTS = "tests";
    public static final String SUBJECT = "subject";
    public static final String DIFFICULTY = "difficulty";
    public static final String DIFFICULTY_ID = "difficulty_id";
    public static final String DURATION = "duration";
    public static final String NUMBER_OF_QUERIES = "number_of_queries";
    public static final String FINISH_TEST_TIME = "finishTime";

    /** Parameters and attributes to work with QuestionDTO */
    public static final String QUESTION_ID = "questionId";
    public static final String QUESTION = "question";
    public static final String QUESTIONS = "questions";
    public static final String QUESTION_TEXT = "questionText";

    /** Parameters and attributes to work with AnswerDTO */
    public static final String ANSWERS = "answers";
    public static final String ANSWER = "answer";
    public static final String ANSWERS_TEXT = "answersText";
    public static final String CORRECT = "correct";
    public static final String NUMBER_OF_ANSWERS = "numberOfAnswers";
    public static final String NUMBER_OF_CORRECT_ANSWERS = "numberOfCorrectAnswers";

    /** Parameters and attributes to work with TestResultDTO */
    public static final String TEST_RESULTS = "testResults";
    public static final String SCORE = "score";

    /** Parameters and attributes to work with sorting, ordering and pagination */
    public static final String SORT_FIELD = "sortField";
    public static final String ORDER = "order";
    public static final String OFFSET = "offset";
    public static final String CURRENT_PAGE = "currentPage";
    public static final String PAGES = "pages";
    public static final String START = "start";
    public static final String END = "end";
    public static final String RECORDS = "records";

    /** Parameters and attributes to send error or message to view */
    public static final String ERROR = "error";
    public static final String MESSAGE = "message";

    /** Parameter to get action name */
    public static final String ACTION = "action";

    /** Parameter to get current path in action */
    public static final String CURRENT_PATH = "current-path";

    /** Parameter to get captcha value */
    public static final String CAPTCHA = "g-recaptcha-response";

    /** Parameter and attribute to get or set locale */
    public static final String LOCALE = "locale";

}
