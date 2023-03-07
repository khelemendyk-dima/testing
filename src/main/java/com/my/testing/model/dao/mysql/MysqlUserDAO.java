package com.my.testing.model.dao.mysql;

import com.my.testing.exceptions.DAOException;
import com.my.testing.model.dao.UserDAO;
import com.my.testing.model.entities.User;
import com.my.testing.model.entities.enums.Role;
import org.apache.logging.log4j.*;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;

import static com.my.testing.model.dao.mysql.constants.SQLFields.*;
import static com.my.testing.model.dao.mysql.constants.UserSQLQueries.*;

/**
 * User DAO class for MySQL database. Matches 'user' table in database.
 *
 * @author Khelemendyk Dmytro
 * @version 1.0
 */
public class MysqlUserDAO implements UserDAO {
    private static final Logger logger = LogManager.getLogger(MysqlUserDAO.class);
    /** An instance of datasource to provide connection to database */
    private final DataSource dataSource;

    public MysqlUserDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Obtains instance of User from database by id
     * @param id value of id field in database
     * @return Optional.ofNullable user is null if there is no user
     * @throws DAOException is wrapper for SQLException
     */
    @Override
    public Optional<User> getById(long id) throws DAOException {
        User user = null;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_USER_BY_ID)) {
            int k = 0;
            preparedStatement.setLong(++k, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    user = createUser(resultSet);
                }
            }
        } catch (SQLException e) {
            logger.error(String.format("Couldn't find user with id=%d because of %s", id, e.getMessage()));
            throw new DAOException(e);
        }

        return Optional.ofNullable(user);
    }

    /**
     * Obtains instance of User from database by email
     * @param email user's email
     * @return Optional.ofNullable user is null if there is no user
     * @throws DAOException is wrapper for SQLException
     */
    @Override
    public Optional<User> getByEmail(String email) throws DAOException {
        User user = null;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_USER_BY_EMAIL)) {
            int k = 0;
            preparedStatement.setString(++k, email);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    user = createUser(resultSet);
                }
            }
        } catch (SQLException e) {
            logger.error(String.format("Couldn't find user with email - %s because of %s", email, e.getMessage()));
            throw new DAOException(e);
        }

        return Optional.ofNullable(user);
    }

    /**
     * Obtains list of all users from database
     * @return users list
     * @throws DAOException is wrapper for SQLException
     */
    @Override
    public List<User> getAll() throws DAOException {
        List<User> users = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_USERS);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                users.add(createUser(resultSet));
            }
        } catch (SQLException e) {
            logger.error(String.format("Couldn't get list of all users because of %s", e.getMessage()));
            throw new DAOException(e);
        }
        return users;
    }

    /**
     * Inserts new user to database
     * @param user concrete entity in implementations
     * @throws DAOException is wrapper for SQLException
     */
    @Override
    public void add(User user) throws DAOException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(ADD_USER)) {
            setStatementFieldsForAddMethod(user, preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(String.format("Couldn't add new user %s because of %s", user.getEmail(), e.getMessage()));
            throw new DAOException(e);
        }
    }

    /**
     * Updates user
     * @param user should contain id, email, name and surname to be updated
     * @throws DAOException is wrapper for SQLException
     */
    @Override
    public void update(User user) throws DAOException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER)) {
            setStatementFieldsForUpdateMethod(user, preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(String.format("Couldn't update user %s because of %s", user.getEmail(), e.getMessage()));
            throw new DAOException(e);
        }
    }

    /**
     * Updates user's password
     * @param user should contain user id and new password
     * @throws DAOException is wrapper for SQLException
     */
    @Override
    public void updatePassword(User user) throws DAOException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_PASSWORD)) {
            int k = 0;
            preparedStatement.setString(++k, user.getPassword());
            preparedStatement.setLong(++k, user.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(String.format("Couldn't update user %s password because of %s", user.getEmail(), e.getMessage()));
            throw new DAOException(e);
        }
    }

    /**
     * Sets new user's role
     * @param email user's email
     * @param role new role for user
     * @throws DAOException is wrapper for SQLException
     */
    @Override
    public void setUserRole(String email, Role role) throws DAOException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SET_USER_ROLE)) {
            int k = 0;
            preparedStatement.setInt(++k, role.getValue());
            preparedStatement.setString(++k, email);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(String.format("Couldn't set role for user %s because of %s", email, e.getMessage()));
            throw new DAOException(e);
        }
    }

    /**
     * Deletes user record in database
     * @param id value of id field in database
     * @throws DAOException is wrapper for SQLException
     */
    @Override
    public void delete(long id) throws DAOException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER)) {
            int k = 0;
            preparedStatement.setLong(++k, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(String.format("Couldn't delete user with id=%d because of %s", id, e.getMessage()));
            throw new DAOException(e);
        }
    }

    /**
     * Creates user entity from result set
     * @param resultSet set that contains necessary data
     * @return User entity
     * @throws SQLException if something go wrong
     */
    private User createUser(ResultSet resultSet) throws SQLException {
        return User.builder()
                .id(resultSet.getLong(ID))
                .email(resultSet.getString(EMAIL))
                .name(resultSet.getString(NAME))
                .surname(resultSet.getString(SURNAME))
                .password(resultSet.getString(PASSWORD))
                .roleId(resultSet.getInt(ROLE_ID))
                .build();
    }

    private void setStatementFieldsForAddMethod(User user, PreparedStatement preparedStatement)
            throws SQLException {
        int k = 0;
        preparedStatement.setString(++k, user.getEmail());
        preparedStatement.setString(++k, user.getPassword());
        preparedStatement.setString(++k, user.getName());
        preparedStatement.setString(++k, user.getSurname());
    }

    private void setStatementFieldsForUpdateMethod(User user, PreparedStatement preparedStatement)
            throws SQLException {
        int k = 0;
        preparedStatement.setString(++k, user.getEmail());
        preparedStatement.setString(++k, user.getName());
        preparedStatement.setString(++k, user.getSurname());
        preparedStatement.setLong(++k, user.getId());
    }
}
