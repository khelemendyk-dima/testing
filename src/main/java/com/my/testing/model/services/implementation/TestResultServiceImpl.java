package com.my.testing.model.services.implementation;

import com.my.testing.dto.TestResultDTO;
import com.my.testing.exceptions.*;
import com.my.testing.model.dao.TestResultDAO;
import com.my.testing.model.entities.TestResult;
import com.my.testing.model.services.TestResultService;

import java.util.*;

import static com.my.testing.utils.ConvertorUtil.*;
import static com.my.testing.utils.ValidatorUtil.getTestResultId;

public class TestResultServiceImpl implements TestResultService {
    private final TestResultDAO testResultDAO;

    public TestResultServiceImpl(TestResultDAO testResultDAO) {
        this.testResultDAO = testResultDAO;
    }

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

    @Override
    public List<TestResultDTO> getAllByUserId(String idString) throws ServiceException {
        List<TestResultDTO> testResultDTOS = new ArrayList<>();
        long userId = getTestResultId(idString);

        try {
            List<TestResult> testResults = testResultDAO.getAllByUserId(userId);
            testResults.forEach(testResult -> testResultDTOS.add(convertTestResultToDTO(testResult)));
        } catch (DAOException e) {
            throw new ServiceException(e);
        }

        return testResultDTOS;
    }

    @Override
    public void update(TestResultDTO entity) throws ServiceException {
        try {
            testResultDAO.update(convertDTOToTestResult(entity));
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void delete(String idString) throws ServiceException {
        long testResultId = getTestResultId(idString);

        try {
            testResultDAO.delete(testResultId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void add(TestResultDTO testResultDTO) throws ServiceException {
        TestResult testResult = convertDTOToTestResult(testResultDTO);
        try {
            testResultDAO.add(testResult);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
