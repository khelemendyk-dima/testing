package com.my.testing.controller.filters.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

import static com.my.testing.controller.actions.constants.Pages.*;

/**
 * Contains pages sets for anonymous user and different roles logged user. Defines if user has access to the page
 *
 * @author Khelemendyk Dmytro
 * @version 1.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DomainPagesSets {
    @Getter private static final Set<String> anonymousUserPages = new HashSet<>();
    @Getter private static final Set<String> blockedUserPages = new HashSet<>();
    @Getter private static final Set<String> studentPages = new HashSet<>();
    @Getter private static final Set<String> adminPages = new HashSet<>();

    static {
        anonymousUserPages.add(CONTROLLER_PAGE);
        anonymousUserPages.add(INDEX_PAGE);
        anonymousUserPages.add(ABOUT_US_PAGE);
        anonymousUserPages.add(CONTACTS_PAGE);
        anonymousUserPages.add(ERROR_PAGE);
        anonymousUserPages.add(SIGN_IN_PAGE);
        anonymousUserPages.add(SIGN_UP_PAGE);
        anonymousUserPages.add(RESET_PASSWORD_PAGE);
    }

    static {
        blockedUserPages.addAll(anonymousUserPages);
        blockedUserPages.add(PROFILE_PAGE);
    }

    static {
        studentPages.addAll(anonymousUserPages);
        studentPages.add(PROFILE_PAGE);
        studentPages.add(EDIT_PROFILE_PAGE);
        studentPages.add(CHANGE_PASSWORD_PAGE);
        studentPages.add(VIEW_TESTS_PAGE);
        studentPages.add(VIEW_TEST_PAGE);
        studentPages.add(SOLVE_TEST_PAGE);
        studentPages.add(VIEW_RESULT_PAGE);
        studentPages.add(VIEW_RESULTS_PAGE);
    }

    static {
        adminPages.addAll(studentPages);
        adminPages.add(CREATE_TEST_PAGE);
        adminPages.add(CREATE_QUESTION_PAGE);
        adminPages.add(EDIT_TEST_PAGE);
        adminPages.add(EDIT_QUESTION_PAGE);
        adminPages.add(EDIT_USER_BY_ADMIN_PAGE);
        adminPages.add(FIND_USER_PAGE);
        adminPages.add(USER_INFO_BY_EMAIL_PAGE);
    }

}
