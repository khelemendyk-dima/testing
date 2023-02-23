package com.my.testing.model.dao;

import com.my.testing.exceptions.DAOException;
import com.my.testing.model.dao.mysql.MysqlTestResultDAO;
import com.my.testing.model.entities.TestResult;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.*;
import org.mockito.quality.Strictness;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

import static com.my.testing.Constants.*;
import static com.my.testing.model.dao.mysql.constants.SQLFields.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class TestResultDAOTest {
    private static TestResult testResultExample;
    @Mock
    DataSource dataSource;
    @Mock
    Connection connection;
    @Mock
    PreparedStatement preparedStatement;
    @Mock
    ResultSet resultSet;

    @BeforeAll
    static void setUp() {
        testResultExample =TestResult.builder()
                .id(ID_VALUE)
                .userId(ID_VALUE)
                .testId(ID_VALUE)
                .result(RESULT_VALUE)
                .testName(TEST_NAME_VALUE)
                .build();
    }


    @BeforeEach
    void initEach() throws SQLException {
        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        doNothing().when(preparedStatement).setInt(anyInt(), anyInt());
        doNothing().when(preparedStatement).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement).setString(anyInt(), anyString());
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
    }

    @Test
    void testGetById() throws SQLException, DAOException {
        TestResultDAO testResultDAO = new MysqlTestResultDAO(dataSource);
        makeResultSetReturnValidTestResult();
        TestResult testResult = testResultDAO.getById(ID_VALUE).orElse(null);
        assertNotNull(testResult);
        assertEquals(testResultExample, testResult);
    }

    @Test
    void testGetByIdAbsent() throws SQLException, DAOException {
        TestResultDAO testResultDAO = new MysqlTestResultDAO(dataSource);
        when(resultSet.next()).thenReturn(false);
        TestResult testResult = testResultDAO.getById(ID_VALUE).orElse(null);
        assertNull(testResult);
    }

    @Test
    void testSqlExceptionGetById() throws SQLException {
        TestResultDAO testResultDAO = new MysqlTestResultDAO(dataSource);
        when(preparedStatement.executeQuery()).thenThrow(SQLException.class);
        assertThrows(DAOException.class, () -> testResultDAO.getById(ID_VALUE));
    }

    @Test
    void testGetAll() throws SQLException, DAOException {
        TestResultDAO testResultDAO = new MysqlTestResultDAO(dataSource);
        makeResultSetReturnValidTestResult();
        List<TestResult> testResults = testResultDAO.getAll();
        assertEquals(ONE, testResults.size());
        assertEquals(testResultExample, testResults.get(0));
    }

    @Test
    void testGetNoTestResults() throws SQLException, DAOException {
        TestResultDAO testResultDAO = new MysqlTestResultDAO(dataSource);
        when(resultSet.next()).thenReturn(false);
        List<TestResult> testResults = testResultDAO.getAll();
        assertEquals(ZERO, testResults.size());
    }

    @Test
    void testSqlExceptionGetAll() throws SQLException {
        TestResultDAO testResultDAO = new MysqlTestResultDAO(dataSource);
        when(preparedStatement.executeQuery()).thenThrow(SQLException.class);
        assertThrows(DAOException.class, testResultDAO::getAll);
    }

    @Test
    void testGetAllByUserId() throws SQLException, DAOException {
        TestResultDAO testResultDAO = new MysqlTestResultDAO(dataSource);
        makeResultSetReturnValidTestResult();
        List<TestResult> testResults = testResultDAO.getAllByUserId(ID_VALUE);
        assertEquals(ONE, testResults.size());
        assertEquals(testResultExample, testResults.get(0));
    }

    @Test
    void testGetAllByUserIdAbsent() throws SQLException, DAOException {
        TestResultDAO testResultDAO = new MysqlTestResultDAO(dataSource);
        when(resultSet.next()).thenReturn(false);
        List<TestResult> testResults = testResultDAO.getAllByUserId(ID_VALUE);
        assertEquals(ZERO, testResults.size());
    }

    @Test
    void testSqlExceptionGetAllByUserId() throws SQLException {
        TestResultDAO testResultDAO = new MysqlTestResultDAO(dataSource);
        when(preparedStatement.executeQuery()).thenThrow(SQLException.class);
        assertThrows(DAOException.class, () -> testResultDAO.getAllByUserId(ID_VALUE));
    }

    @Test
    void testAdd() {
        TestResultDAO testResultDAO = new MysqlTestResultDAO(dataSource);
        assertDoesNotThrow(() -> testResultDAO.add(testResultExample));
    }

    @Test
    void testSqlExceptionAdd() throws SQLException {
        TestResultDAO testResultDAO = new MysqlTestResultDAO(dataSource);
        when(preparedStatement.executeUpdate()).thenThrow(SQLException.class);
        assertThrows(DAOException.class, () -> testResultDAO.add(testResultExample));
    }

    @Test
    void testUpdate() {
        TestResultDAO testResultDAO = new MysqlTestResultDAO(dataSource);
        assertDoesNotThrow(() -> testResultDAO.update(testResultExample));
    }

    @Test
    void testSqlExceptionUpdate() throws SQLException {
        TestResultDAO testResultDAO = new MysqlTestResultDAO(dataSource);
        when(preparedStatement.executeUpdate()).thenThrow(SQLException.class);
        assertThrows(DAOException.class, () -> testResultDAO.update(testResultExample));
    }

    @Test
    void testDelete() {
        TestResultDAO testResultDAO = new MysqlTestResultDAO(dataSource);
        assertDoesNotThrow(() -> testResultDAO.delete(ID_VALUE));
    }

    @Test
    void testSqlExceptionDelete() throws SQLException {
        TestResultDAO testResultDAO = new MysqlTestResultDAO(dataSource);
        when(preparedStatement.executeUpdate()).thenThrow(SQLException.class);
        assertThrows(DAOException.class, () -> testResultDAO.delete(ID_VALUE));
    }

    private void makeResultSetReturnValidTestResult() throws SQLException {
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getLong(ID)).thenReturn(ID_VALUE);
        when(resultSet.getLong(USER_ID)).thenReturn(ID_VALUE);
        when(resultSet.getLong(TEST_ID)).thenReturn(ID_VALUE);
        when(resultSet.getFloat(RESULT)).thenReturn(RESULT_VALUE);
        when(resultSet.getString(TEST_NAME)).thenReturn(TEST_NAME_VALUE);
    }
}
