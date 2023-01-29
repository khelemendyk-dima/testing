package com.my.testing.model.dao;

import com.my.testing.exceptions.DAOException;
import com.my.testing.model.entities.Test;

import java.util.List;

public interface TestDAO extends DAO<Test> {
    List<Test> getSorted(String query) throws DAOException;

    int getNumberOfRecords(String filter) throws DAOException;
}
