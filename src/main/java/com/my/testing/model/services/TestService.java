package com.my.testing.model.services;

import com.my.testing.dto.TestDTO;
import com.my.testing.exceptions.ServiceException;

import java.util.List;

/**
 * TestService interface.
 * Implements all methods in concrete TestService
 *
 * @author Khelemendyk Dmytro
 * @version 1.0
 */
public interface TestService extends Service<TestDTO> {

    /**
     * Calls DAO to add new entity
     * @param testDTO DTO to be added as entity to database
     * @throws ServiceException - may wrap DAOException or be thrown by another mistakes
     */
    void add(TestDTO testDTO) throws ServiceException;

    /**
     * Calls DAO to get sorted, filtered and limited list of DTOs
     * @param query to obtain necessary DTOs
     * @return list of TestDTOs that match demands
     * @throws ServiceException - may wrap DAOException or be thrown by another mistakes
     */
    List<TestDTO> getSorted(String query) throws ServiceException;

    /**
     * Calls DAO to get number of all records matches filter
     * @param filter conditions for such Test
     * @return number of records that matches demands
     * @throws ServiceException - may wrap DAOException or be thrown by another mistakes
     */
    int getNumberOfRecords(String filter) throws ServiceException;
}
