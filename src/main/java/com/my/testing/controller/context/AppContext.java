package com.my.testing.controller.context;

import lombok.Getter;
import com.my.testing.model.services.*;

public class AppContext {
    private final ServiceFactory serviceFactory = ServiceFactory.getInstance();
    @Getter private final UserService userService = serviceFactory.getUserService();
    @Getter private final TestService testService = serviceFactory.getTestService();

    public static AppContext getAppContext() {
        return new AppContext();
    }

    private AppContext() {}
}
