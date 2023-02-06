package com.my.testing.model.services.implementation;

import com.my.testing.dto.TestDTO;
import com.my.testing.exceptions.*;
import com.my.testing.model.dao.TestDAO;
import com.my.testing.model.entities.Test;
import com.my.testing.model.services.TestService;

import java.util.ArrayList;
import java.util.List;

import static com.my.testing.utils.ConvertorUtil.convertDTOToTest;
import static com.my.testing.utils.ConvertorUtil.convertTestToTDO;
import static com.my.testing.utils.ValidatorUtil.getTestId;
import static com.my.testing.utils.ValidatorUtil.validateTestName;

public class TestServiceImpl implements TestService {
    private final TestDAO testDAO;

    public TestServiceImpl(TestDAO testDAO) {
        this.testDAO = testDAO;
    }

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

    @Override
    public void update(TestDTO entity) throws ServiceException {
        validateTestName(entity.getName());
        try {
            testDAO.update(convertDTOToTest(entity));
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

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
