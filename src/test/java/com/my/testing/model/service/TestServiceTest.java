package com.my.testing.model.service;

import com.my.testing.dto.TestDTO;
import com.my.testing.exceptions.*;
import com.my.testing.model.dao.TestDAO;
import com.my.testing.model.services.TestService;
import com.my.testing.model.services.implementation.TestServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.util.*;

import static com.my.testing.Constants.*;
import static com.my.testing.exceptions.constants.Message.ENTER_CORRECT_NAME;
import static com.my.testing.utils.QueryBuilderUtil.testQueryBuilder;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TestServiceTest {
    private final TestDAO testDAO = mock(TestDAO.class);
    private final TestService testService = new TestServiceImpl(testDAO);

    @Test
    void testAdd() throws DAOException {
        doNothing().when(testDAO).add(any());
        assertDoesNotThrow(() -> testService.add(getTestDTO()));
    }

    @ParameterizedTest
    @ValueSource(strings = {"a_%", "$a", "ё"})
    void testAddIncorrectTestName(String name) throws DAOException {
        doNothing().when(testDAO).add(any());
        TestDTO testDTO = getTestDTO();
        testDTO.setName(name);
        IncorrectFormatException e = assertThrows(IncorrectFormatException.class,
                () -> testService.add(testDTO));
        assertEquals(ENTER_CORRECT_NAME, e.getMessage());
    }

    @ParameterizedTest
    @NullAndEmptySource
    void testAddNullEmptyName(String name) throws DAOException {
        doNothing().when(testDAO).add(any());
        TestDTO testDTO = getTestDTO();
        testDTO.setName(name);
        IncorrectFormatException e = assertThrows(IncorrectFormatException.class,
                () -> testService.add(testDTO));
        assertEquals(ENTER_CORRECT_NAME, e.getMessage());
    }

    @Test
    void testGetSorted() throws DAOException, ServiceException {
        List<com.my.testing.model.entities.Test> tests = new ArrayList<>();
        List<TestDTO> testDTOS = new ArrayList<>();
        tests.add(getTest());
        testDTOS.add(getTestDTO());
        String query = testQueryBuilder().getQuery();
        when(testDAO.getSorted(query)).thenReturn(tests);
        assertIterableEquals(testDTOS, testService.getSorted(query));
    }

    @Test
    void testSQLErrorGetSorted() throws DAOException {
        doThrow(DAOException.class).when(testDAO).getSorted(anyString());
        assertThrows(ServiceException.class, () -> testService.getSorted(anyString()));
    }

    @Test
    void testGetNumberOfRecords() throws DAOException, ServiceException {
        String filter = testQueryBuilder().getRecordQuery();
        when(testDAO.getNumberOfRecords(filter)).thenReturn(THREE);
        assertEquals(THREE, testService.getNumberOfRecords(filter));
    }

    @Test
    void testSQLErrorGetNumberOfRecords() throws DAOException {
        doThrow(DAOException.class).when(testDAO).getNumberOfRecords(anyString());
        assertThrows(ServiceException.class, () -> testService.getNumberOfRecords("filter"));
    }

    @Test
    void testGetById() throws DAOException, ServiceException {
        when(testDAO.getById(ID_VALUE)).thenReturn(Optional.of(getTest()));
        assertEquals(getTestDTO(), testService.getById(String.valueOf(ID_VALUE)));
    }

    @Test
    void testSQLErrorGetById() throws DAOException {
        doThrow(DAOException.class).when(testDAO).getById(anyLong());
        assertThrows(ServiceException.class, () -> testService.getById(String.valueOf(ID_VALUE)));
    }

    @Test
    void testGetByIdNoTest() throws DAOException {
        when(testDAO.getById(ID_VALUE)).thenReturn(Optional.empty());
        assertThrows(NoSuchTestException.class, () -> testService.getById(String.valueOf(ID_VALUE)));
    }

    @Test
    void testGetAll() throws DAOException, ServiceException {
        when(testDAO.getAll()).thenReturn(List.of(getTest()));
        assertIterableEquals(List.of(getTestDTO()), testService.getAll());
    }

    @Test
    void testSQLErrorGetAll() throws DAOException {
        doThrow(DAOException.class).when(testDAO).getAll();
        assertThrows(ServiceException.class, testService::getAll);
    }

    @Test
    void testUpdate() throws DAOException {
        doNothing().when(testDAO).update(any());
        assertDoesNotThrow(() -> testService.update(getTestDTO()));
    }

    @Test
    void testSQLErrorUpdate() throws DAOException {
        doThrow(DAOException.class).when(testDAO).update(any());
        assertThrows(ServiceException.class, () -> testService.update(getTestDTO()));
    }

    @ParameterizedTest
    @ValueSource(strings = {"a_%", "$a", "ё"})
    void testUpdateIncorrectTestName(String name) throws DAOException {
        doNothing().when(testDAO).update(any());
        TestDTO testDTO = getTestDTO();
        testDTO.setName(name);
        IncorrectFormatException e = assertThrows(IncorrectFormatException.class,
                () -> testService.update(testDTO));
        assertEquals(ENTER_CORRECT_NAME, e.getMessage());
    }

    @Test
    void testDelete() throws DAOException {
        doNothing().when(testDAO).delete(anyLong());
        assertDoesNotThrow(() -> testService.delete(String.valueOf(ID_VALUE)));
    }

    @Test
    void testSQLErrorDelete() throws DAOException {
        doThrow(DAOException.class).when(testDAO).delete(anyLong());
        assertThrows(ServiceException.class, () -> testService.delete(String.valueOf(ID_VALUE)));
    }

    private TestDTO getTestDTO() {
        return TestDTO.builder()
                .id(ID_VALUE)
                .name(TEST_NAME_VALUE)
                .subject(SUBJECT_ENGLISH)
                .difficulty(DIFFICULTY_MEDIUM)
                .duration(DURATION_VALUE)
                .numberOfQueries(NUMBER_OF_QUERIES_VALUE)
                .build();
    }

    private com.my.testing.model.entities.Test getTest() {
        return com.my.testing.model.entities.Test.builder()
                .id(ID_VALUE)
                .name(TEST_NAME_VALUE)
                .subjectId(SUBJECT_ID_VALUE)
                .difficultyId(DIFFICULTY_ID_VALUE)
                .duration(DURATION_VALUE)
                .numberOfQueries(NUMBER_OF_QUERIES_VALUE)
                .build();
    }
}
