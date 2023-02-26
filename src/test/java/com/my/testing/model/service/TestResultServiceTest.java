package com.my.testing.model.service;

import com.my.testing.dto.TestResultDTO;
import com.my.testing.exceptions.*;
import com.my.testing.model.dao.TestResultDAO;
import com.my.testing.model.entities.TestResult;
import com.my.testing.model.services.TestResultService;
import com.my.testing.model.services.implementation.TestResultServiceImpl;
import org.junit.jupiter.api.Test;

import java.util.*;

import static com.my.testing.Constants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class TestResultServiceTest {
    private final TestResultDAO testResultDAO = mock(TestResultDAO.class);
    private final TestResultService testResultService = new TestResultServiceImpl(testResultDAO);

    @Test
    void testAdd() throws DAOException {
        doNothing().when(testResultDAO).add(any());
        assertDoesNotThrow(() -> testResultService.add(getTestResultDTO()));
    }

    @Test
    void testGetAllByUserId() throws DAOException, ServiceException {
        when(testResultDAO.getAllByUserId(ID_VALUE)).thenReturn(List.of(getTestResult()));
        assertIterableEquals(List.of(getTestResultDTO()), testResultService.getAllByUserId(String.valueOf(ID_VALUE)));
    }

    @Test
    void testSQLErrorGetAllByUserId() throws DAOException {
        doThrow(DAOException.class).when(testResultDAO).getAllByUserId(anyLong());
        assertThrows(ServiceException.class, () -> testResultService.getAllByUserId(String.valueOf(ID_VALUE)));
    }

    @Test
    void testGetById() throws DAOException, ServiceException {
        when(testResultDAO.getById(ID_VALUE)).thenReturn(Optional.of(getTestResult()));
        assertEquals(getTestResultDTO(), testResultService.getById(String.valueOf(ID_VALUE)));
    }

    @Test
    void testSQLErrorGetById() throws DAOException {
        doThrow(DAOException.class).when(testResultDAO).getById(anyLong());
        assertThrows(ServiceException.class, () -> testResultService.getById(String.valueOf(ID_VALUE)));
    }

    @Test
    void testGetByIdNoTestResult() throws DAOException {
        when(testResultDAO.getById(ID_VALUE)).thenReturn(Optional.empty());
        assertThrows(NoSuchTestResultException.class, () -> testResultService.getById(String.valueOf(ID_VALUE)));
    }

    @Test
    void testGetAll() throws DAOException, ServiceException {
        when(testResultDAO.getAll()).thenReturn(List.of(getTestResult()));
        assertIterableEquals(List.of(getTestResultDTO()), testResultService.getAll());
    }

    @Test
    void testSQLErrorGetAll() throws DAOException {
        doThrow(DAOException.class).when(testResultDAO).getAll();
        assertThrows(ServiceException.class, testResultService::getAll);
    }

    @Test
    void testUpdate() throws DAOException {
        doNothing().when(testResultDAO).update(any());
        assertDoesNotThrow(() -> testResultService.update(getTestResultDTO()));
    }

    @Test
    void testSQLErrorUpdate() throws DAOException {
        doThrow(DAOException.class).when(testResultDAO).update(any());
        assertThrows(ServiceException.class, () -> testResultService.update(getTestResultDTO()));
    }

    @Test
    void testDelete() throws DAOException {
        doNothing().when(testResultDAO).delete(anyLong());
        assertDoesNotThrow(() -> testResultService.delete(String.valueOf(ID_VALUE)));
    }

    @Test
    void testSQLErrorDelete() throws DAOException {
        doThrow(DAOException.class).when(testResultDAO).delete(anyLong());
        assertThrows(ServiceException.class, () -> testResultService.delete(String.valueOf(ID_VALUE)));
    }
    
    private TestResultDTO getTestResultDTO() {
        return TestResultDTO.builder()
                .id(ID_VALUE)
                .userId(ID_VALUE)
                .testId(ID_VALUE)
                .testName(TEST_NAME_VALUE)
                .result(RESULT_VALUE)
                .build();
    }
    
    private TestResult getTestResult() {
        return TestResult.builder()
                .id(ID_VALUE)
                .userId(ID_VALUE)
                .testId(ID_VALUE)
                .testName(TEST_NAME_VALUE)
                .result(RESULT_VALUE)
                .build();
    }
}
