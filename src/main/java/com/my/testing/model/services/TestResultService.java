package com.my.testing.model.services;

import com.my.testing.dto.TestResultDTO;
import com.my.testing.exceptions.ServiceException;

import java.util.List;

/**
 * TestResultService interface.
 * Implements all methods in concrete TestResultService
 *
 * @author Khelemendyk Dmytro
 * @version 1.0
 */
public interface TestResultService extends Service<TestResultDTO> {

    /**
     * Calls DAO to add new entity
     * @param testResultDTO DTO to be added as entity to database
     * @throws ServiceException - may wrap DAOException or be thrown by another mistakes
     */
    void add(TestResultDTO testResultDTO) throws ServiceException;

    /**
     * Obtains list of all test results that user passed by id
     * @param idString id as a String to validate and convert to long
     * @return list of TestResultDTOs
     * @throws ServiceException - may wrap DAOException or be thrown by another mistakes
     */
    List<TestResultDTO> getAllByUserId(String idString) throws ServiceException;
}
