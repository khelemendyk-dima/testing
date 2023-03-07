package com.my.testing.model.dao;

import com.my.testing.exceptions.DAOException;
import java.util.*;

/**
 * DAO interface.
 * Implement methods in all concrete DAOs
 *
 * @author Khelemendyk Dmytro
 * @version 1.0
 * @param <T> the type of entities
 */
public interface DAO<T> {

    /**
     * Obtains instance of entity from database
     * @param id value of id field in database
     * @return Optional.ofNullable entity is null if there is no entity
     * @throws DAOException is wrapper for SQLException
     */
    Optional<T> getById(long id) throws DAOException;

    /**
     * Obtains list of all entities from database
     * @return list of entities
     * @throws DAOException is wrapper for SQLException
     */
    List<T> getAll() throws DAOException;

    /**
     * Inserts record into database
     * @param t concrete entity in implementations
     * @throws DAOException is wrapper for SQLException
     */
    void add(T t) throws DAOException;

    /**
     * Updates entity
     * @param t should contain all necessary fields
     * @throws DAOException is wrapper for SQLException
     */
    void update(T t) throws DAOException;

    /**
     * Deletes record in database
     * @param id value of id field in database
     * @throws DAOException is wrapper for SQLException
     */
    void delete(long id) throws DAOException;
}
