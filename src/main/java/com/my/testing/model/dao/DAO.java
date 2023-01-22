package com.my.testing.model.dao;

import com.my.testing.exceptions.DAOException;
import java.util.*;

public interface DAO<T> {

    Optional<T> getById(long id) throws DAOException;

    List<T> getAll() throws DAOException;

    void add(T t) throws DAOException;

    void update(T t) throws DAOException;

    void delete(long id) throws DAOException;

}
