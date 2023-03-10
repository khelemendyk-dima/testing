package com.my.testing.controller.actions.constants;

import lombok.*;

/**
 * This is ActionNames class. It contains all action names
 *
 * @author Khelemendyk Dmytro
 * @version 1.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ActionNames {
    /** Any user's actions */
    public static final String DEFAULT_ACTION = "default";
    public static final String SIGN_UP_ACTION = "sign-up";
    public static final String SIGN_IN_ACTION = "sign-in";
    public static final String RESET_PASSWORD_ACTION = "reset-password";

    /** Logged user's actions */
    public static final String SIGN_OUT_ACTION = "sign-out";
    public static final String EDIT_PROFILE_ACTION = "edit-profile";
    public static final String CHANGE_PASSWORD_ACTION = "change-password";
    public static final String VIEW_TESTS_ACTION = "view-tests";
    public static final String SEARCH_TEST_ACTION = "search-test";
    public static final String SOLVE_TEST_ACTION = "solve-test";
    public static final String START_TEST_ACTION = "start-test";
    public static final String END_TEST_ACTION = "end-test";
    public static final String VIEW_RESULTS_ACTION = "view-results";
    public static final String TEST_TO_PDF_ACTION = "test-pdf";

    /** Admins actions */
    public static final String FIND_USER_ACTION = "find-user";
    public static final String SET_ROLE_ACTION = "set-role";
    public static final String EDIT_USER_BY_ADMIN_ACTION = "edit-user-by-admin";
    public static final String CREATE_TEST_ACTION = "create-test";
    public static final String EDIT_TEST_ACTION = "edit-test";
    public static final String DELETE_TEST_ACTION = "delete-test";
    public static final String CREATE_QUESTION_ACTION = "create-question";
    public static final String EDIT_QUESTION_ACTION = "edit-question";
    public static final String DELETE_QUESTION_ACTION = "delete-question";
    public static final String SEARCH_QUESTION_ACTION = "search-question";
}
