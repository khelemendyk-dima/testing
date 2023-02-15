package com.my.testing.model.dao.mysql;

import com.my.testing.exceptions.DAOException;
import com.my.testing.model.connection.DataSource;
import com.my.testing.model.dao.TestResultDAO;
import com.my.testing.model.entities.TestResult;

import java.sql.*;
import java.util.*;

import static com.my.testing.model.dao.mysql.constants.SQLFields.*;
import static com.my.testing.model.dao.mysql.constants.TestResultSQLQueries.*;

public class MysqlTestResultDAO implements TestResultDAO {
    @Override
    public Optional<TestResult> getById(long id) throws DAOException {
        TestResult testResult = null;
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_TEST_RESULT_BY_ID)) {
            int k = 0;
            preparedStatement.setLong(++k, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    testResult = createTestResult(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }

        return Optional.ofNullable(testResult);
    }

    @Override
    public List<TestResult> getAll() throws DAOException {
        List<TestResult> testResults = new ArrayList<>();

        try (Connection connection = DataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(GET_TEST_RESULTS)) {
            while (resultSet.next()) {
                testResults.add(createTestResult(resultSet));
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }

        return testResults;
    }

    @Override
    public List<TestResult> getAllByUserId(long userId) throws DAOException {
        List<TestResult> testResults = new ArrayList<>();

        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_TEST_RESULTS_BY_USER_ID)) {
            int k = 0;
            preparedStatement.setLong(++k, userId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    testResults.add(createTestResult(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }

        return testResults;
    }

    @Override
    public void add(TestResult testResult) throws DAOException {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(ADD_TEST_RESULT)) {
            setStatementFieldsForAddMethod(testResult, preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void update(TestResult testResult) throws DAOException {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_TEST_RESULT)) {
            setStatementFieldsForUpdateMethod(testResult, preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void delete(long id) throws DAOException {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_TEST_RESULT)) {
            int k = 0;
            preparedStatement.setLong(++k, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

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
