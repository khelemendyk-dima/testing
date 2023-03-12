package com.my.testing.controller.filters.domain;

import lombok.*;

import java.util.*;

import static com.my.testing.controller.actions.constants.ActionNames.*;

/**
 * Contains action sets for anonymous user and different roles logged users. Defines if user has access to the action
 *
 * @author Khelemendyk Dmytro
 * @version 1.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DomainActionsSets {
    @Getter private static final Set<String> anonymousUserActions = new HashSet<>();
    @Getter private static final Set<String> blockedUserActions = new HashSet<>();
    @Getter private static final Set<String> studentActions = new HashSet<>();
    @Getter private static final Set<String> adminActions = new HashSet<>();

    static {
        anonymousUserActions.add(DEFAULT_ACTION);
        anonymousUserActions.add(SIGN_IN_ACTION);
        anonymousUserActions.add(SIGN_UP_ACTION);
        anonymousUserActions.add(RESET_PASSWORD_ACTION);
    }

    static {
        blockedUserActions.addAll(anonymousUserActions);
        blockedUserActions.add(SIGN_OUT_ACTION);
    }

    static {
        studentActions.addAll(anonymousUserActions);
        studentActions.add(SIGN_OUT_ACTION);
        studentActions.add(EDIT_PROFILE_ACTION);
        studentActions.add(CHANGE_PASSWORD_ACTION);
        studentActions.add(VIEW_TESTS_ACTION);
        studentActions.add(SEARCH_TEST_ACTION);
        studentActions.add(START_TEST_ACTION);
        studentActions.add(SOLVE_TEST_ACTION);
        studentActions.add(END_TEST_ACTION);
        studentActions.add(VIEW_RESULTS_ACTION);
    }

    static {
        adminActions.addAll(studentActions);
        adminActions.add(FIND_USER_ACTION);
        adminActions.add(SET_ROLE_ACTION);
        adminActions.add(EDIT_USER_BY_ADMIN_ACTION);
        adminActions.add(CREATE_TEST_ACTION);
        adminActions.add(CREATE_QUESTION_ACTION);
        adminActions.add(SEARCH_QUESTION_ACTION);
        adminActions.add(EDIT_TEST_ACTION);
        adminActions.add(EDIT_QUESTION_ACTION);
        adminActions.add(DELETE_TEST_ACTION);
        adminActions.add(DELETE_QUESTION_ACTION);
        adminActions.add(TEST_TO_PDF_ACTION);
    }
}
