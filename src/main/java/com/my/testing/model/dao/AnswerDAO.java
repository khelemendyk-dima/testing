package com.my.testing.model.dao;

import com.my.testing.exceptions.DAOException;
import com.my.testing.model.entities.Answer;

import java.util.List;

/**
 * Answer DAO interface.
 * Implements methods due to database type
 *
 * @author Khelemendyk Dmytro
 * @version 1.0
 */
public interface AnswerDAO extends DAO<Answer> {

    /**
     * Obtains list of all answers by question id
     * @param questionId value of question id in database
     * @return list of answers
     * @throws DAOException is wrapper for SQLException
     */
    List<Answer> getAllByQuestionId(long questionId) throws DAOException;

    /**
     * Deletes all answers by question id
     * @param questionId value of question id in database
     * @throws DAOException is wrapper for SQLException
     */
    void deleteAllByQuestionId(long questionId) throws DAOException;
}
