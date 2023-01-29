package com.my.testing.model.dao.mysql;

import com.my.testing.model.dao.DAOFactory;
import com.my.testing.model.dao.TestDAO;
import com.my.testing.model.dao.UserDAO;

public class MysqlDAOFactory extends DAOFactory {
    private UserDAO userDAO;
    private TestDAO testDAO;

    @Override
    public UserDAO getUserDAO() {
        if (userDAO == null) {
            userDAO = new MysqlUserDAO();
        }

        return userDAO;
    }

    @Override
    public TestDAO getTestDAO() {
        if (testDAO == null) {
            testDAO = new MysqlTestDAO();
        }

        return testDAO;
    }
}
