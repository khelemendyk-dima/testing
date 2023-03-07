package com.my.testing.model.dao;

import com.my.testing.exceptions.DAOException;
import com.my.testing.model.entities.Question;

import java.util.List;

/**
 * Question DAO interface.
 * Implement methods due to database type
 *
 * @author Khelemendyk Dmytro
 * @version 1.0
 */
public interface QuestionDAO extends DAO<Question> {

    /**
     * Obtains list of questions by test id
     * @param testId value of test id in database
     * @return list of questions
     * @throws DAOException is wrapper for SQLException
     */
    List<Question> getAllByTestId(long testId) throws DAOException;
}
