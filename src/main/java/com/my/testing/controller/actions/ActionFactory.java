package com.my.testing.controller.actions;

import com.my.testing.controller.actions.implementation.*;
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
    }

    private ActionFactory() {}

    public static ActionFactory getActionFactory() {
        return ACTION_FACTORY;
    }

    public Action createAction(String actionName) {
        return ACTION_MAP.getOrDefault(actionName, new DefaultAction());
    }
}
