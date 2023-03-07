package com.my.testing.model.services.implementation;

import com.my.testing.dto.TestResultDTO;
import com.my.testing.exceptions.*;
import com.my.testing.model.dao.TestResultDAO;
import com.my.testing.model.entities.TestResult;
import com.my.testing.model.services.TestResultService;

import java.util.*;

import static com.my.testing.utils.ConvertorUtil.*;
import static com.my.testing.utils.ValidatorUtil.getTestResultId;
import static com.my.testing.utils.ValidatorUtil.getUserId;

/**
 * Implementation of TestResultService interface.
 * Contains testResultDAO field to work with TestResultDAO
 *
 * @author Khelemendyk Dmytro
 * @version 1.0
 */
public class TestResultServiceImpl implements TestResultService {
    private final TestResultDAO testResultDAO;

    /**
     * @param testResultDAO DAO to work with database
     */
    public TestResultServiceImpl(TestResultDAO testResultDAO) {
        this.testResultDAO = testResultDAO;
    }

    /**
     * Gets TestResultDTO from action and calls DAO to add new entity.
     * @param testResultDTO DTO to be added as entity to database
     * @throws ServiceException may wrap DAOException
     */
    @Override
    public void add(TestResultDTO testResultDTO) throws ServiceException {
        TestResult testResult = convertDTOToTestResult(testResultDTO);
        try {
            testResultDAO.add(testResult);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Obtains instance of TestResult from DAO by id. Checks if id valid. Converts TestResult to TestResultDTO
     * @param idString id as a String to validate and convert to long
     * @return TestResultDTO instance
     * @throws ServiceException may wrap DAOException or be thrown as NoSuchTestResultException
     */
    @Override
    public TestResultDTO getById(String idString) throws ServiceException {
        TestResultDTO testResultDTO;
        long testResultId = getTestResultId(idString);

        try {
            TestResult testResult = testResultDAO.getById(testResultId).orElseThrow(NoSuchTestResultException::new);
            testResultDTO = convertTestResultToDTO(testResult);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }

        return testResultDTO;
    }

    /**
     * Obtains list of all instances of TestResult from DAO. Converts TestResults to TestResultDTOs
     * @return list of TestResultDTOs
     * @throws ServiceException may wrap DAOException
     */
    @Override
    public List<TestResultDTO> getAll() throws ServiceException {
        List<TestResultDTO> testResultDTOS = new ArrayList<>();

        try {
            List<TestResult> testResults = testResultDAO.getAll();
            testResults.forEach(testResult -> testResultDTOS.add(convertTestResultToDTO(testResult)));
        } catch (DAOException e) {
            throw new ServiceException(e);
        }

        return testResultDTOS;
    }

    /**
     * Obtains list of all instances of TestResult that user passed by user's id from DAO. Validates user's id.
     * Converts TestResults to TestResultDTOs
     * @param idString id as a String to validate and convert to long
     * @return list of TestResultDTOs
     * @throws ServiceException may wrap DAOException or be thrown as NoSuchUserException
     */
    @Override
    public List<TestResultDTO> getAllByUserId(String idString) throws ServiceException {
        List<TestResultDTO> testResultDTOS = new ArrayList<>();
        long userId = getUserId(idString);

        try {
            List<TestResult> testResults = testResultDAO.getAllByUserId(userId);
            testResults.forEach(testResult -> testResultDTOS.add(convertTestResultToDTO(testResult)));
        } catch (DAOException e) {
            throw new ServiceException(e);
        }

        return testResultDTOS;
    }

    /**
     * Updates TestResult's result, userId, testId. Converts TestResultDTO to TestResult
     * @param entity DTO to be updated
     * @throws ServiceException - may wrap DAOException
     */
    @Override
    public void update(TestResultDTO entity) throws ServiceException {
        try {
            testResultDAO.update(convertDTOToTestResult(entity));
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Deletes TestResult entity from database. Validates id.
     * @param idString id as a String to validate and convert to long
     * @throws ServiceException - may wrap DAOException or be thrown as NoSuchTestResultException
     */
    @Override
    public void delete(String idString) throws ServiceException {
        long testResultId = getTestResultId(idString);

        try {
            testResultDAO.delete(testResultId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
