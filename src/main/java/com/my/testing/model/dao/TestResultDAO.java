package com.my.testing.model.dao;

import com.my.testing.exceptions.DAOException;
import com.my.testing.model.entities.TestResult;

import java.util.List;

public interface TestResultDAO extends DAO<TestResult> {
    List<TestResult> getAllByUserId(long userId) throws DAOException;
}
