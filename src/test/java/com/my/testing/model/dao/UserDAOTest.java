package com.my.testing.model.dao;

import com.my.testing.exceptions.DAOException;
import com.my.testing.model.dao.mysql.MysqlUserDAO;
import com.my.testing.model.entities.User;
import com.my.testing.model.entities.enums.Role;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.*;
import org.mockito.quality.Strictness;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

import static com.my.testing.Constants.*;
import static com.my.testing.Constants.ROLE_ID_VALUE;
import static com.my.testing.model.dao.mysql.constants.SQLFields.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class UserDAOTest {
    private static User userExample;
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
        userExample = User.builder()
                          .id(ID_VALUE)
                          .email(EMAIL_VALUE)
                          .name(NAME_VALUE)
                          .surname(SURNAME_VALUE)
                          .password(PASSWORD_VALUE)
                          .roleId(ROLE_ID_VALUE)
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
    void testGetById() throws DAOException, SQLException {
        UserDAO userDAO = new MysqlUserDAO(dataSource);
        makeResultSetReturnValidUser();
        User resultUser = userDAO.getById(ID_VALUE).orElse(null);
        assertNotNull(resultUser);
        assertEquals(userExample, resultUser);
    }

    @Test
    void testGetByIdAbsent() throws DAOException, SQLException {
        UserDAO userDAO = new MysqlUserDAO(dataSource);
        when(resultSet.next()).thenReturn(false);
        User resultUser = userDAO.getById(ID_VALUE).orElse(null);
        assertNull(resultUser);
    }

    @Test
    void testSqlExceptionGetById() throws SQLException {
        UserDAO userDAO = new MysqlUserDAO(dataSource);
        when(preparedStatement.executeQuery()).thenThrow(SQLException.class);
        assertThrows(DAOException.class, () -> userDAO.getById(ID_VALUE));
    }

    @Test
    void testGetByEmail() throws SQLException, DAOException {
        UserDAO userDAO = new MysqlUserDAO(dataSource);
        makeResultSetReturnValidUser();
        User resultUser = userDAO.getByEmail(EMAIL_VALUE).orElse(null);
        assertNotNull(resultUser);
        assertEquals(userExample, resultUser);
    }

    @Test
    void testGetByEmailAbsent() throws SQLException, DAOException {
        UserDAO userDAO = new MysqlUserDAO(dataSource);
        when(resultSet.next()).thenReturn(false);
        User resultUser = userDAO.getByEmail(EMAIL_VALUE).orElse(null);
        assertNull(resultUser);
    }

    @Test
    void testSqlExceptionGetByEmail() throws SQLException {
        UserDAO userDAO = new MysqlUserDAO(dataSource);
        when(preparedStatement.executeQuery()).thenThrow(SQLException.class);
        assertThrows(DAOException.class, () -> userDAO.getByEmail(EMAIL_VALUE));
    }

    @Test
    void testGetAll() throws SQLException, DAOException {
        UserDAO userDAO = new MysqlUserDAO(dataSource);
        makeResultSetReturnValidUser();
        List<User> users = userDAO.getAll();
        assertEquals(ONE, users.size());
        assertEquals(userExample, users.get(0));
    }

    @Test
    void testGetNoUsers() throws SQLException, DAOException {
        UserDAO userDAO = new MysqlUserDAO(dataSource);
        when(resultSet.next()).thenReturn(false);
        List<User> users = userDAO.getAll();
        assertEquals(ZERO, users.size());
    }

    @Test
    void testSqlExceptionGetAll() throws SQLException {
        UserDAO userDAO = new MysqlUserDAO(dataSource);
        when(resultSet.next()).thenThrow(SQLException.class);
        assertThrows(DAOException.class, userDAO::getAll);
    }

    @Test
    void testAdd() {
        UserDAO userDAO = new MysqlUserDAO(dataSource);
        assertDoesNotThrow(() -> userDAO.add(userExample));
    }

    @Test
    void testSqlExceptionAdd() throws SQLException {
        UserDAO userDAO = new MysqlUserDAO(dataSource);
        when(preparedStatement.executeUpdate()).thenThrow(SQLException.class);
        assertThrows(DAOException.class, () -> userDAO.add(userExample));
    }

    @Test
    void testUpdate() {
        UserDAO userDAO = new MysqlUserDAO(dataSource);
        assertDoesNotThrow(() -> userDAO.update(userExample));
    }

    @Test
    void testSqlExceptionUpdate() throws SQLException {
        UserDAO userDAO = new MysqlUserDAO(dataSource);
        when(preparedStatement.executeUpdate()).thenThrow(SQLException.class);
        assertThrows(DAOException.class, () -> userDAO.update(userExample));
    }

    @Test
    void testUpdatePassword() {
        UserDAO userDAO = new MysqlUserDAO(dataSource);
        assertDoesNotThrow(() -> userDAO.updatePassword(userExample));
    }
    @Test
    void testSqlExceptionUpdatePassword() throws SQLException {
        UserDAO userDAO = new MysqlUserDAO(dataSource);
        when(preparedStatement.executeUpdate()).thenThrow(SQLException.class);
        assertThrows(DAOException.class, () -> userDAO.updatePassword(userExample));
    }

    @Test
    void testSetUserRole() {
        UserDAO userDAO = new MysqlUserDAO(dataSource);
        assertDoesNotThrow(() -> userDAO.setUserRole(EMAIL_VALUE, Role.STUDENT));
    }

    @Test
    void testSqlExceptionSetUserRole() throws SQLException {
        UserDAO userDAO = new MysqlUserDAO(dataSource);
        when(preparedStatement.executeUpdate()).thenThrow(SQLException.class);
        assertThrows(DAOException.class, () -> userDAO.setUserRole(EMAIL_VALUE, Role.STUDENT));
    }

    @Test
    void testDelete() {
        UserDAO userDAO = new MysqlUserDAO(dataSource);
        assertDoesNotThrow(() -> userDAO.delete(ID_VALUE));
    }

    @Test
    void testSqlExceptionDelete() throws SQLException {
        UserDAO userDAO = new MysqlUserDAO(dataSource);
        when(preparedStatement.executeUpdate()).thenThrow(SQLException.class);
        assertThrows(DAOException.class, () -> userDAO.delete(ID_VALUE));
    }

    private void makeResultSetReturnValidUser() throws SQLException {
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getLong(ID)).thenReturn(ID_VALUE);
        when(resultSet.getString(EMAIL)).thenReturn(EMAIL_VALUE);
        when(resultSet.getString(NAME)).thenReturn(NAME_VALUE);
        when(resultSet.getString(SURNAME)).thenReturn(SURNAME_VALUE);
        when(resultSet.getString(PASSWORD)).thenReturn(PASSWORD_VALUE);
        when(resultSet.getInt(ROLE_ID)).thenReturn(ROLE_ID_VALUE);
    }
}
