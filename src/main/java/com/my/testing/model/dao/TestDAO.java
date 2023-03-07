package com.my.testing.model.dao;

import com.my.testing.exceptions.DAOException;
import com.my.testing.model.entities.Test;

import java.util.List;

/**
 * Test DAO interface.
 * Implements methods due to database type
 *
 * @author Khelemendyk Dmytro
 * @version 1.0
 */
public interface TestDAO extends DAO<Test> {

    /**
     * Obtains sorted and limited list of tests from database
     * @param query should contain filters, order, limits for pagination
     * @return tests list that matches demands
     * @throws DAOException is wrapper for SQLException
     */
    List<Test> getSorted(String query) throws DAOException;

    /**
     * Obtains number of all records matching filter
     * @param filter should contain 'where' to specify query
     * @return number of records
     * @throws DAOException is wrapper for SQLException
     */
    int getNumberOfRecords(String filter) throws DAOException;
}
