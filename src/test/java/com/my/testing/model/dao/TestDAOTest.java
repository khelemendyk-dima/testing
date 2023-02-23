package com.my.testing.model.dao;

import com.my.testing.exceptions.DAOException;
import com.my.testing.model.dao.mysql.MysqlTestDAO;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class TestDAOTest {
    private static com.my.testing.model.entities.Test testExample;
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
        testExample = com.my.testing.model.entities.Test.builder()
                .id(ID_VALUE)
                .name(TEST_NAME_VALUE)
                .subjectId(SUBJECT_ID_VALUE)
                .difficultyId(DIFFICULTY_ID_VALUE)
                .duration(DURATION_VALUE)
                .numberOfQueries(NUMBER_OF_QUERIES_VALUE)
                .build();
    }

    @BeforeEach
    void initEach() throws SQLException {
        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(connection.prepareStatement(anyString(), eq(Statement.RETURN_GENERATED_KEYS)))
                .thenReturn(preparedStatement);
        doNothing().when(preparedStatement).setInt(anyInt(), anyInt());
        doNothing().when(preparedStatement).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement).setString(anyInt(), anyString());
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(preparedStatement.getGeneratedKeys()).thenReturn(resultSet);
    }

    @Test
    void testGetById() throws SQLException, DAOException {
        TestDAO testDAO = new MysqlTestDAO(dataSource);
        makeResultSetReturnValidTest();
        com.my.testing.model.entities.Test resultTest = testDAO.getById(ID_VALUE).orElse(null);
        assertNotNull(resultTest);
        assertEquals(testExample, resultTest);
    }

    @Test
    void testGetByIdAbsent() throws SQLException, DAOException {
        TestDAO testDAO = new MysqlTestDAO(dataSource);
        when(resultSet.next()).thenReturn(false);
        com.my.testing.model.entities.Test resultTest = testDAO.getById(ID_VALUE).orElse(null);
        assertNull(resultTest);
    }

    @Test
    void testSqlExceptionGetById() throws SQLException {
        TestDAO testDAO = new MysqlTestDAO(dataSource);
        when(preparedStatement.executeQuery()).thenThrow(SQLException.class);
        assertThrows(DAOException.class, () -> testDAO.getById(ID_VALUE));
    }

    @Test
    void testGetSorted() throws DAOException, SQLException {
        TestDAO testDAO = new MysqlTestDAO(dataSource);
        makeResultSetReturnValidTest();
        List<com.my.testing.model.entities.Test> tests = testDAO.getSorted("query");
        assertEquals(ONE, tests.size());
        assertEquals(testExample, tests.get(0));
    }

    @Test
    void testGetSortedNoTests() throws DAOException, SQLException {
        TestDAO testDAO = new MysqlTestDAO(dataSource);
        when(resultSet.next()).thenReturn(false);
        List<com.my.testing.model.entities.Test> tests = testDAO.getSorted("query");
        assertEquals(ZERO, tests.size());
    }

    @Test
    void testSqlExceptionGetSorted() throws SQLException {
        TestDAO testDAO = new MysqlTestDAO(dataSource);
        when(preparedStatement.executeQuery()).thenThrow(SQLException.class);
        assertThrows(DAOException.class, () -> testDAO.getSorted("query"));
    }

    @Test
    void testGetNumberOfRecords() throws DAOException, SQLException {
        TestDAO testDAO = new MysqlTestDAO(dataSource);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt(NUMBER_OF_RECORDS)).thenReturn(100);
        int records = testDAO.getNumberOfRecords("filter");
        assertEquals(100, records);
    }

    @Test
    void testSqlExceptionGetNumberOfRecords() throws SQLException {
        TestDAO testDAO = new MysqlTestDAO(dataSource);
        when(preparedStatement.executeQuery()).thenThrow(SQLException.class);
        assertThrows(DAOException.class, () -> testDAO.getNumberOfRecords("filter"));
    }

    @Test
    void testGetAll() throws SQLException, DAOException {
        TestDAO testDAO = new MysqlTestDAO(dataSource);
        makeResultSetReturnValidTest();
        List<com.my.testing.model.entities.Test> tests = testDAO.getAll();
        assertEquals(ONE, tests.size());
        assertEquals(testExample, tests.get(0));
    }

    @Test
    void testGetNoTests() throws SQLException, DAOException {
        TestDAO testDAO = new MysqlTestDAO(dataSource);
        when(resultSet.next()).thenReturn(false);
        List<com.my.testing.model.entities.Test> tests = testDAO.getAll();
        assertEquals(ZERO, tests.size());
    }

    @Test
    void testSqlExceptionGetAll() throws SQLException {
        TestDAO testDAO = new MysqlTestDAO(dataSource);
        when(preparedStatement.executeQuery()).thenThrow(SQLException.class);
        assertThrows(DAOException.class, testDAO::getAll);
    }

    @Test
    void testAdd() {
        TestDAO testDAO = new MysqlTestDAO(dataSource);
        assertDoesNotThrow(() -> testDAO.add(testExample));
    }

    @Test
    void testSqlExceptionAdd() throws SQLException {
        TestDAO testDAO = new MysqlTestDAO(dataSource);
        when(preparedStatement.executeUpdate()).thenThrow(SQLException.class);
        assertThrows(DAOException.class, () -> testDAO.add(testExample));
    }

    @Test
    void testUpdate() {
        TestDAO testDAO = new MysqlTestDAO(dataSource);
        assertDoesNotThrow(() -> testDAO.update(testExample));
    }

    @Test
    void testSqlExceptionUpdate() throws SQLException {
        TestDAO testDAO = new MysqlTestDAO(dataSource);
        when(preparedStatement.executeUpdate()).thenThrow(SQLException.class);
        assertThrows(DAOException.class, () -> testDAO.update(testExample));
    }

    @Test
    void testDelete() {
        TestDAO testDAO = new MysqlTestDAO(dataSource);
        assertDoesNotThrow(() -> testDAO.delete(ONE));
    }

    @Test
    void testSqlExceptionDelete() throws SQLException {
        TestDAO testDAO = new MysqlTestDAO(dataSource);
        when(preparedStatement.executeUpdate()).thenThrow(SQLException.class);
        assertThrows(DAOException.class, () -> testDAO.delete(ONE));
    }

    private void makeResultSetReturnValidTest() throws SQLException {
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getLong(ID)).thenReturn(ID_VALUE);
        when(resultSet.getString(NAME)).thenReturn(TEST_NAME_VALUE);
        when(resultSet.getInt(SUBJECT_ID)).thenReturn(SUBJECT_ID_VALUE);
        when(resultSet.getInt(DIFFICULTY_ID)).thenReturn(DIFFICULTY_ID_VALUE);
        when(resultSet.getInt(DURATION)).thenReturn(DURATION_VALUE);
        when(resultSet.getInt(NUMBER_OF_QUERIES)).thenReturn(NUMBER_OF_QUERIES_VALUE);
    }


}
