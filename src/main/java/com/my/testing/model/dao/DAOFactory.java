package com.my.testing.model.dao;

import com.my.testing.model.dao.mysql.MysqlDAOFactory;

import javax.sql.DataSource;

public abstract class DAOFactory {
    private static DAOFactory instance;

    protected DAOFactory() {}

    public static synchronized DAOFactory getInstance(DataSource dataSource) {
        if (instance == null) {
            instance = new MysqlDAOFactory(dataSource);
        }

        return instance;
    }

    public abstract UserDAO getUserDAO();
    public abstract TestDAO getTestDAO();
    public abstract QuestionDAO getQuestionDAO();
    public abstract AnswerDAO getAnswerDAO();
    public abstract TestResultDAO getTestResultDAO();
}
