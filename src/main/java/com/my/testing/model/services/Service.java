package com.my.testing.model.services;

import com.my.testing.exceptions.ServiceException;
import java.util.List;

/**
 * Service interface.
 * Implements methods in all concrete services
 *
 * @author Khelemendyk Dmytro
 * @version 1.0
 * @param <T> the type of DTO
 */
public interface Service<T> {

    /**
     * Obtains instance of DTO from DAO by id
     * @param idString id as a String to validate and convert to long
     * @return DTO instance
     * @throws ServiceException - may wrap DAOException or be thrown by another mistakes
     */
    T getById(String idString) throws ServiceException;

    /**
     * Obtains list of all instances of DTO from DAO
     * @return list of DTOs
     * @throws ServiceException - may wrap DAOException or be thrown by another mistakes
     */
    List<T> getAll() throws ServiceException;

    /**
     * Updates entity
     * @param entity DTO to be updated
     * @throws ServiceException - may wrap DAOException or be thrown by another mistakes
     */
    void update(T entity) throws ServiceException;

    /**
     * Deletes entity
     * @param idString id as a String to validate and convert to long
     * @throws ServiceException - may wrap DAOException or be thrown by another mistakes
     */
    void delete(String idString) throws ServiceException;
}