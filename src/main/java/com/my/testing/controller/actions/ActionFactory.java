package com.my.testing.controller.actions;

import com.my.testing.controller.actions.implementation.admin.*;
import com.my.testing.controller.actions.implementation.base.*;
import com.my.testing.controller.actions.implementation.user.*;
import com.my.testing.controller.context.AppContext;

import java.util.*;

import static com.my.testing.controller.actions.constants.ActionNames.*;

public final class ActionFactory {
    private static final ActionFactory ACTION_FACTORY = new ActionFactory();
    private static final Map<String, Action> ACTION_MAP = new HashMap<>();
    private static final AppContext APP_CONTEXT = AppContext.getAppContext();

    static {
        ACTION_MAP.put(DEFAULT_ACTION, new DefaultAction());
        ACTION_MAP.put(SIGN_UP_ACTION, new SignUpAction(APP_CONTEXT));
        ACTION_MAP.put(SIGN_IN_ACTION, new SignInAction(APP_CONTEXT));
        ACTION_MAP.put(SIGN_OUT_ACTION, new SignOutAction());
        ACTION_MAP.put(EDIT_PROFILE_ACTION, new EditProfileAction(APP_CONTEXT));
        ACTION_MAP.put(CHANGE_PASSWORD_ACTION, new ChangePasswordAction(APP_CONTEXT));
        ACTION_MAP.put(FIND_USER_ACTION, new FindUserAction(APP_CONTEXT));
        ACTION_MAP.put(SET_ROLE_ACTION, new SetRoleAction(APP_CONTEXT));
        ACTION_MAP.put(EDIT_USER_BY_ADMIN_ACTION, new EditUserByAdminAction(APP_CONTEXT));
        ACTION_MAP.put(CREATE_TEST_ACTION, new CreateTestAction(APP_CONTEXT));
        ACTION_MAP.put(VIEW_TESTS_ACTION, new ViewTestsAction(APP_CONTEXT));
        ACTION_MAP.put(SEARCH_TEST_ACTION, new SearchTestAction(APP_CONTEXT));
        ACTION_MAP.put(CREATE_QUESTION_ACTION, new CreateQuestionAction(APP_CONTEXT));
        ACTION_MAP.put(DELETE_QUESTION_ACTION, new DeleteQuestionAction(APP_CONTEXT));
        ACTION_MAP.put(DELETE_TEST_ACTION, new DeleteTestAction(APP_CONTEXT));
        ACTION_MAP.put(EDIT_TEST_ACTION, new EditTestAction(APP_CONTEXT));
        ACTION_MAP.put(SEARCH_QUESTION_ACTION, new SearchQuestionAction(APP_CONTEXT));
        ACTION_MAP.put(EDIT_QUESTION_ACTION, new EditQuestionAction(APP_CONTEXT));
        ACTION_MAP.put(START_TEST_ACTION, new StartTestAction());
        ACTION_MAP.put(SOLVE_TEST_ACTION, new SolveTestAction(APP_CONTEXT));
        ACTION_MAP.put(END_TEST_ACTION, new EndTestAction(APP_CONTEXT));
        ACTION_MAP.put(VIEW_RESULTS_ACTION, new ViewResultsAction(APP_CONTEXT));
        ACTION_MAP.put(TEST_TO_PDF_ACTION, new TestToPdfAction(APP_CONTEXT));
    }

    private ActionFactory() {}

    public static ActionFactory getActionFactory() {
        return ACTION_FACTORY;
    }

    public Action createAction(String actionName) {
        return ACTION_MAP.getOrDefault(actionName, new DefaultAction());
    }
}
