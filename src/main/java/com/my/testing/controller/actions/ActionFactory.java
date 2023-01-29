package com.my.testing.controller.actions;

import com.my.testing.controller.actions.implementation.admin.CreateTestAction;
import com.my.testing.controller.actions.implementation.admin.EditUserByAdminAction;
import com.my.testing.controller.actions.implementation.admin.FindUserAction;
import com.my.testing.controller.actions.implementation.admin.SetRoleAction;
import com.my.testing.controller.actions.implementation.base.*;
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
    }

    private ActionFactory() {}

    public static ActionFactory getActionFactory() {
        return ACTION_FACTORY;
    }

    public Action createAction(String actionName) {
        return ACTION_MAP.getOrDefault(actionName, new DefaultAction());
    }
}
