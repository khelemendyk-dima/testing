package com.my.testing.model.dao.mysql;

import com.my.testing.exceptions.DAOException;
import com.my.testing.model.dao.TestResultDAO;
import com.my.testing.model.entities.TestResult;
import org.apache.logging.log4j.*;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;

import static com.my.testing.model.dao.mysql.constants.SQLFields.*;
import static com.my.testing.model.dao.mysql.constants.TestResultSQLQueries.*;

/**
 * Test result DAO class for MySQL database. Matches 'test_result' table in database.
 *
 * @author Khelemendyk Dmytro
 * @version 1.0
 */
public class MysqlTestResultDAO implements TestResultDAO {
    private static final Logger logger = LogManager.getLogger(MysqlTestResultDAO.class);
    /** An instance of datasource to provide connection to database */
    private final DataSource dataSource;

    public MysqlTestResultDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Obtains instance of TestResult from database by id
     * @param id value of id field in database
     * @return Optional.ofNullable test result is null if there is no such result
     * @throws DAOException is wrapper for SQLException
     */
    @Override
    public Optional<TestResult> getById(long id) throws DAOException {
        TestResult testResult = null;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_TEST_RESULT_BY_ID)) {
            int k = 0;
            preparedStatement.setLong(++k, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    testResult = createTestResult(resultSet);
                }
            }
        } catch (SQLException e) {
            logger.error(String.format("Couldn't find test result with id=%d because of %s", id, e.getMessage()));
            throw new DAOException(e);
        }

        return Optional.ofNullable(testResult);
    }

    /**
     * Obtains list of all test results from database
     * @return test results list
     * @throws DAOException is wrapper for SQLException
     */
    @Override
    public List<TestResult> getAll() throws DAOException {
        List<TestResult> testResults = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_TEST_RESULTS);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                testResults.add(createTestResult(resultSet));
            }
        } catch (SQLException e) {
            logger.error(String.format("Couldn't get list of all test results because of %s", e.getMessage()));
            throw new DAOException(e);
        }

        return testResults;
    }

    /**
     * Obtains list of all test results by user id from database
     * @param userId value of user id in database
     * @return test results list
     * @throws DAOException is wrapper for SQLException
     */
    @Override
    public List<TestResult> getAllByUserId(long userId) throws DAOException {
        List<TestResult> testResults = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_TEST_RESULTS_BY_USER_ID)) {
            int k = 0;
            preparedStatement.setLong(++k, userId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    testResults.add(createTestResult(resultSet));
                }
            }
        } catch (SQLException e) {
            logger.error(String.format("Couldn't get list of all test results by user with id=%d because of %s", userId, e.getMessage()));
            throw new DAOException(e);
        }

        return testResults;
    }

    /**
     * Inserts new test result to database
     * @param testResult concrete entity in implementations
     * @throws DAOException is wrapper for SQLException
     */
    @Override
    public void add(TestResult testResult) throws DAOException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(ADD_TEST_RESULT)) {
            setStatementFieldsForAddMethod(testResult, preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(String.format("Couldn't add new test result because of %s", e.getMessage()));
            throw new DAOException(e);
        }
    }

    /**
     * Updates test result
     * @param testResult should contain all necessary fields
     * @throws DAOException is wrapper for SQLException
     */
    @Override
    public void update(TestResult testResult) throws DAOException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_TEST_RESULT)) {
            setStatementFieldsForUpdateMethod(testResult, preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(String.format("Couldn't update test result because of %s", e.getMessage()));
            throw new DAOException(e);
        }
    }

    /**
     * Deletes test result record in database
     * @param id value of id field in database
     * @throws DAOException is wrapper for SQLException
     */
    @Override
    public void delete(long id) throws DAOException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_TEST_RESULT)) {
            int k = 0;
            preparedStatement.setLong(++k, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(String.format("Couldn't delete test result with id=%d because of %s", id, e.getMessage()));
            throw new DAOException(e);
        }
    }

    /**
     * Creates test result entity from result set
     * @param resultSet set that contains necessary data
     * @return TestResult entity
     * @throws SQLException if something go wrong
     */
    private TestResult createTestResult(ResultSet resultSet) throws SQLException {
        return TestResult.builder()
                .id(resultSet.getLong(ID))
                .userId(resultSet.getLong(USER_ID))
                .testId(resultSet.getLong(TEST_ID))
                .result(resultSet.getFloat(RESULT))
                .testName(resultSet.getString(TEST_NAME))
                .build();
    }

    private void setStatementFieldsForAddMethod(TestResult testResult, PreparedStatement preparedStatement) throws SQLException {
        int k = 0;
        preparedStatement.setLong(++k, testResult.getUserId());
        preparedStatement.setLong(++k, testResult.getTestId());
        preparedStatement.setFloat(++k, testResult.getResult());
    }

    private void setStatementFieldsForUpdateMethod(TestResult testResult, PreparedStatement preparedStatement) throws SQLException {
        int k = 0;
        preparedStatement.setLong(++k, testResult.getUserId());
        preparedStatement.setLong(++k, testResult.getTestId());
        preparedStatement.setFloat(++k, testResult.getResult());
        preparedStatement.setLong(++k, testResult.getId());
    }
}
