package com.my.testing.model.dao;

import com.my.testing.exceptions.DAOException;
import com.my.testing.model.entities.TestResult;

import java.util.List;

/**
 * User DAO interface.
 * Implements methods due to database type
 *
 * @author Khelemendyk Dmytro
 * @version 1.0
 */
public interface TestResultDAO extends DAO<TestResult> {

    /**
     * Obtains list of test results that user passed
     * @param userId value of user id in database
     * @return list of test results
     * @throws DAOException is wrapper for SQLException
     */
    List<TestResult> getAllByUserId(long userId) throws DAOException;
}
