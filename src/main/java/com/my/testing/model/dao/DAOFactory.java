package com.my.testing.model.dao;

import com.my.testing.model.dao.mysql.MysqlDAOFactory;

import javax.sql.DataSource;

/**
 * Abstract factory that provides concrete factories to obtains DAOs
 *
 * @author Khelemendyk Dmytro
 * @version 1.0
 */
public abstract class DAOFactory {
    /** A single instance of the factory (Singleton pattern) */
    private static DAOFactory instance;

    /** Constructor should be used only in subclasses */
    protected DAOFactory() {}

    /**
     * Obtains single instance of the class. Synchronized to avoid multithreading collisions
     * @param dataSource datasource to connect database
     * @return concrete DAO factory
     */
    public static synchronized DAOFactory getInstance(DataSource dataSource) {
        if (instance == null) {
            instance = new MysqlDAOFactory(dataSource);
        }

        return instance;
    }

    /**
     * Obtains concrete instance of DAO class
     * @return UserDAO for required database type
     */
    public abstract UserDAO getUserDAO();

    /**
     * Obtains concrete instance of DAO class
     * @return TestDAO for required database type
     */
    public abstract TestDAO getTestDAO();

    /**
     * Obtains concrete instance of DAO class
     * @return QuestionDAO for required database type
     */
    public abstract QuestionDAO getQuestionDAO();

    /**
     * Obtains concrete instance of DAO class
     * @return AnswerDAO for required database type
     */
    public abstract AnswerDAO getAnswerDAO();

    /**
     * Obtains concrete instance of DAO class
     * @return TestResultDAO for required database type
     */
    public abstract TestResultDAO getTestResultDAO();
}
