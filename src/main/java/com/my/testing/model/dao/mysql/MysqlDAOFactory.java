package com.my.testing.model.dao.mysql;

import com.my.testing.model.dao.*;

import javax.sql.DataSource;

public class MysqlDAOFactory extends DAOFactory {
    private final DataSource dataSource;
    private UserDAO userDAO;
    private TestDAO testDAO;
    private QuestionDAO questionDAO;
    private AnswerDAO answerDAO;
    private TestResultDAO testResultDAO;

    public MysqlDAOFactory(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    @Override
    public UserDAO getUserDAO() {
        if (userDAO == null) {
            userDAO = new MysqlUserDAO(dataSource);
        }

        return userDAO;
    }

    @Override
    public TestDAO getTestDAO() {
        if (testDAO == null) {
            testDAO = new MysqlTestDAO(dataSource);
        }

        return testDAO;
    }

    @Override
    public QuestionDAO getQuestionDAO() {
        if (questionDAO == null) {
            questionDAO = new MysqlQuestionDAO(dataSource);
        }

        return questionDAO;
    }

    @Override
    public AnswerDAO getAnswerDAO() {
        if (answerDAO == null) {
            answerDAO = new MysqlAnswerDAO(dataSource);
        }

        return answerDAO;
    }

    @Override
    public TestResultDAO getTestResultDAO() {
        if (testResultDAO == null) {
            testResultDAO = new MysqlTestResultDAO(dataSource);
        }

        return testResultDAO;
    }
}
