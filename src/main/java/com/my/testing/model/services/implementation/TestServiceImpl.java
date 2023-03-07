package com.my.testing.model.services.implementation;

import com.my.testing.dto.TestDTO;
import com.my.testing.exceptions.*;
import com.my.testing.model.dao.TestDAO;
import com.my.testing.model.entities.Test;
import com.my.testing.model.services.TestService;

import java.util.*;

import static com.my.testing.utils.ConvertorUtil.*;
import static com.my.testing.utils.ValidatorUtil.*;

/**
 * Implementation of TestService interface.
 * Contains testDAO field to work with TestDAO
 *
 * @author Khelemendyk Dmytro
 * @version 1.0
 */
public class TestServiceImpl implements TestService {
    private final TestDAO testDAO;

    /**
     * @param testDAO DAO to work with database
     */
    public TestServiceImpl(TestDAO testDAO) {
        this.testDAO = testDAO;
    }

    /**
     * Gets TestDTO from action and calls DAO to add new entity. Validates test's name
     * Converts TestDTO to Test
     * @param testDTO DTO to be added as entity to database
     * @throws ServiceException - may wrap DAOException or be thrown as IncorrectFormatException with specific message
     */
    @Override
    public void add(TestDTO testDTO) throws ServiceException {
        validateTestName(testDTO.getName());
        Test test = convertDTOToTest(testDTO);
        try {
            testDAO.add(test);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        testDTO.setId(test.getId());
    }

    /**
     * Calls DAO to get sorted, filtered and limited list of DTOs. Converts Tests to TestDTOs
     * @param query to obtain necessary DTOs
     * @return list of TestDTOs that matches demands
     * @throws ServiceException - may wrap DAOException
     */
    @Override
    public List<TestDTO> getSorted(String query) throws ServiceException {
        List<TestDTO> testDTOS = new ArrayList<>();
        try {
            List<Test> tests = testDAO.getSorted(query);
            tests.forEach(test -> testDTOS.add(convertTestToTDO(test)));
        } catch (DAOException e) {
            throw new ServiceException(e);
        }

        return testDTOS;
    }

    /**
     * Calls DAO to get number of all records that matches filter
     * @param filter conditions for such Test
     * @return number of records that matches demands
     * @throws ServiceException - may wrap DAOException
     */
    @Override
    public int getNumberOfRecords(String filter) throws ServiceException {
        int records;
        try {
            records = testDAO.getNumberOfRecords(filter);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }

        return records;
    }

    /**
     * Obtains instance of Test from DAO by id. Checks if id valid. Converts Test to TestDTO
     * @param idString id as a String to validate and convert to long
     * @return TestDTO instance
     * @throws ServiceException - may wrap DAOException or be thrown as NoSuchTestException
     */
    @Override
    public TestDTO getById(String idString) throws ServiceException {
        TestDTO testDTO;
        long testId = getTestId(idString);
        try {
            Test test = testDAO.getById(testId).orElseThrow(NoSuchTestException::new);
            testDTO = convertTestToTDO(test);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }

        return testDTO;
    }

    /**
     * Obtains list of all instances of Test from DAO. Converts Tests to TestDTOs
     * @return list of TestDTOs
     * @throws ServiceException - may wrap DAOException
     */
    @Override
    public List<TestDTO> getAll() throws ServiceException {
        List<TestDTO> testDTOS = new ArrayList<>();

        try {
            List<Test> tests = testDAO.getAll();
            tests.forEach(test -> testDTOS.add(convertTestToTDO(test)));
        } catch (DAOException e) {
            throw new ServiceException(e);
        }

        return testDTOS;
    }

    /**
     * Updates Test's name, subject, difficulty and duration. Validates TestDTO. Converts TestDTO to Test
     * @param entity DTO to be updated
     * @throws ServiceException - may wrap DAOException or be thrown as IncorrectFormatException
     */
    @Override
    public void update(TestDTO entity) throws ServiceException {
        validateTestName(entity.getName());
        try {
            testDAO.update(convertDTOToTest(entity));
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Deletes Test entity from database. Validates id
     * @param idString id as a String to validate and convert to long
     * @throws ServiceException - may wrap DAOException or be thrown as NoSuchTestException
     */
    @Override
    public void delete(String idString) throws ServiceException {
        long testId = getTestId(idString);

        try {
            testDAO.delete(testId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
