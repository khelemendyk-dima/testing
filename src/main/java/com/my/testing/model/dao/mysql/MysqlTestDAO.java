package com.my.testing.model.dao.mysql;

import com.my.testing.exceptions.DAOException;
import com.my.testing.model.connection.DataSource;
import com.my.testing.model.dao.TestDAO;
import com.my.testing.model.entities.Test;

import java.sql.*;
import java.util.*;

import static com.my.testing.model.dao.mysql.constants.SQLFields.*;
import static com.my.testing.model.dao.mysql.constants.TestSQLQueries.*;

public class MysqlTestDAO implements TestDAO {
    @Override
    public Optional<Test> getById(long id) throws DAOException {
        Test test = null;
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_TEST_BY_ID)) {
            int k = 0;
            preparedStatement.setLong(++k, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    test = createTest(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }

        return Optional.ofNullable(test);
    }

    @Override
    public List<Test> getSorted(String query) throws DAOException {
        List<Test> tests = new ArrayList<>();
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(String.format(GET_SORTED, query));
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                tests.add(createTest(resultSet));
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }

        return tests;
    }

    @Override
    public int getNumberOfRecords(String filter) throws DAOException {
        int numberOfRecords = 0;
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(String.format(GET_NUMBER_OF_RECORDS, filter));
             ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                numberOfRecords = resultSet.getInt(NUMBER_OF_RECORDS);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }

        return numberOfRecords;
    }

    @Override
    public List<Test> getAll() throws DAOException {
        List<Test> tests = new ArrayList<>();
        try (Connection connection = DataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(GET_TESTS)) {
            while (resultSet.next()) {
                tests.add(createTest(resultSet));
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return tests;
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public void add(Test test) throws DAOException {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(ADD_TEST, Statement.RETURN_GENERATED_KEYS)) {
            setStatementFieldsForAddMethod(test, preparedStatement);
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                test.setId(resultSet.getLong(1));
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void update(Test test) throws DAOException {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_TEST)) {
            setStatementFieldsForUpdateMethod(test, preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void delete(long id) throws DAOException {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_TEST)) {
            int k = 0;
            preparedStatement.setLong(++k, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    private Test createTest(ResultSet resultSet) throws SQLException {
        return Test.builder()
                .id(resultSet.getLong(ID))
                .name(resultSet.getString(NAME))
                .subjectId(resultSet.getInt(SUBJECT_ID))
                .difficultyId(resultSet.getInt(DIFFICULTY_ID))
                .duration(resultSet.getInt(DURATION))
                .numberOfQueries(resultSet.getInt(NUMBER_OF_QUERIES))
                .build();
    }

    private void setStatementFieldsForAddMethod(Test test, PreparedStatement preparedStatement) throws SQLException {
        int k = 0;
        preparedStatement.setString(++k, test.getName());
        preparedStatement.setInt(++k, test.getSubjectId());
        preparedStatement.setInt(++k, test.getDifficultyId());
        preparedStatement.setInt(++k, test.getDuration());
    }

    private void setStatementFieldsForUpdateMethod(Test test, PreparedStatement preparedStatement) throws SQLException {
        int k = 0;
        preparedStatement.setString(++k, test.getName());
        preparedStatement.setInt(++k, test.getSubjectId());
        preparedStatement.setInt(++k, test.getDifficultyId());
        preparedStatement.setInt(++k, test.getDuration());
        preparedStatement.setLong(++k, test.getId());
    }
}
