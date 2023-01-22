package com.my.testing.model.services;

import com.my.testing.model.dao.DAOFactory;
import com.my.testing.model.services.implementation.UserServiceImpl;

public class ServiceFactory {

    private final UserService userService;

    private ServiceFactory() {
        DAOFactory daoFactory = DAOFactory.getInstance();
        userService = new UserServiceImpl(daoFactory.getUserDAO());
    }

    public static ServiceFactory getInstance() {
        return new ServiceFactory();
    }

    public UserService getUserService() {
        return userService;
    }
}
