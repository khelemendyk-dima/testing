package com.my.testing.model.dao.mysql;

import com.my.testing.model.dao.*;

public class MysqlDAOFactory extends DAOFactory {
    private UserDAO userDAO;
    private TestDAO testDAO;
    private QuestionDAO questionDAO;
    private AnswerDAO answerDAO;

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

    @Override
    public QuestionDAO getQuestionDAO() {
        if (questionDAO == null) {
            questionDAO = new MysqlQuestionDAO();
        }

        return questionDAO;
    }

    @Override
    public AnswerDAO getAnswerDAO() {
        if (answerDAO == null) {
            answerDAO = new MysqlAnswerDAO();
        }

        return answerDAO;
    }
}
