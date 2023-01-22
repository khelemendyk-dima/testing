package com.my.testing.model.dao.mysql;

import com.my.testing.model.dao.DAOFactory;
import com.my.testing.model.dao.UserDAO;

public class MysqlDAOFactory extends DAOFactory {
    private UserDAO userDAO;

    @Override
    public UserDAO getUserDAO() {
        if (userDAO == null) {
            userDAO = new MysqlUserDAO();
        }
        return userDAO;
    }
}
