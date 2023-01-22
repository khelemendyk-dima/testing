package com.my.testing.model.dao;

import com.my.testing.model.dao.mysql.MysqlDAOFactory;

public abstract class DAOFactory {
    private static DAOFactory instance;

    protected DAOFactory() {}

    public static synchronized DAOFactory getInstance() {
        if (instance == null) {
            instance = new MysqlDAOFactory();
        }

        return instance;
    }

    public abstract UserDAO getUserDAO();
}
