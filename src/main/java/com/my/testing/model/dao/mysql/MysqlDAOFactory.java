package com.my.testing.model.dao.mysql;

import com.my.testing.model.dao.*;

import javax.sql.DataSource;

/**
 * MySQL factory that provides MySQL DAOs
 *
 * @author Khelemendyk Dmytro
 * @version 1.0
 */
public class MysqlDAOFactory extends DAOFactory {
    /** An instance of datasource to provide connection to database */
    private final DataSource dataSource;

    /** A single instance of the userDAO (Singleton pattern) */
    private UserDAO userDAO;

    /** A single instance of the testDAO (Singleton pattern) */
    private TestDAO testDAO;

    /** A single instance of the questionDAO (Singleton pattern) */
    private QuestionDAO questionDAO;

    /** A single instance of the answerDAO (Singleton pattern) */
    private AnswerDAO answerDAO;

    /** A single instance of the testResultDAO (Singleton pattern) */
    private TestResultDAO testResultDAO;

    public MysqlDAOFactory(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Obtains single instance of the UserDAO
     * @return MysqlUserDAO
     */
    @Override
    public UserDAO getUserDAO() {
        if (userDAO == null) {
            userDAO = new MysqlUserDAO(dataSource);
        }

        return userDAO;
    }

    /**
     * Obtains single instance of the TestDAO
     * @return MysqlTestDAO
     */
    @Override
    public TestDAO getTestDAO() {
        if (testDAO == null) {
            testDAO = new MysqlTestDAO(dataSource);
        }

        return testDAO;
    }

    /**
     * Obtains single instance of the QuestionDAO
     * @return MysqlQuestionDAO
     */
    @Override
    public QuestionDAO getQuestionDAO() {
        if (questionDAO == null) {
            questionDAO = new MysqlQuestionDAO(dataSource);
        }

        return questionDAO;
    }

    /**
     * Obtains single instance of the AnswerDAO
     * @return MysqlAnswerDAO
     */
    @Override
    public AnswerDAO getAnswerDAO() {
        if (answerDAO == null) {
            answerDAO = new MysqlAnswerDAO(dataSource);
        }

        return answerDAO;
    }

    /**
     * Obtains single instance of the TestResultDAO
     * @return MysqlTestResultDAO
     */
    @Override
    public TestResultDAO getTestResultDAO() {
        if (testResultDAO == null) {
            testResultDAO = new MysqlTestResultDAO(dataSource);
        }

        return testResultDAO;
    }
}
