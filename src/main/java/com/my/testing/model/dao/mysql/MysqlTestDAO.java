package com.my.testing.model.dao.mysql;

import com.my.testing.exceptions.DAOException;
import com.my.testing.model.dao.TestDAO;
import com.my.testing.model.entities.Test;
import org.apache.logging.log4j.*;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;

import static com.my.testing.model.dao.mysql.constants.SQLFields.*;
import static com.my.testing.model.dao.mysql.constants.TestSQLQueries.*;

/**
 * Test DAO class for MySQL database. Matches 'test' table in database.
 *
 * @author Khelemendyk Dmytro
 * @version 1.0
 */
public class MysqlTestDAO implements TestDAO {
    private static final Logger logger = LogManager.getLogger(MysqlTestDAO.class);
    /** An instance of datasource to provide connection to database */
    private final DataSource dataSource;

    public MysqlTestDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Obtains instance of Test from database by id
     * @param id value of id field in database
     * @return Optional.ofNullable test is null if there is no test
     * @throws DAOException is wrapper for SQLException
     */
    @Override
    public Optional<Test> getById(long id) throws DAOException {
        Test test = null;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_TEST_BY_ID)) {
            int k = 0;
            preparedStatement.setLong(++k, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    test = createTest(resultSet);
                }
            }
        } catch (SQLException e) {
            logger.error(String.format("Couldn't find test with id=%d because of %s", id, e.getMessage()));
            throw new DAOException(e);
        }

        return Optional.ofNullable(test);
    }

    /**
     * Obtains sorted and limited list of tests from database
     * @param query should contain filters, order, limits for pagination
     * @return tests list that matches demands. Will be empty if there are no tests
     * @throws DAOException is wrapper for SQLException
     */
    @Override
    public List<Test> getSorted(String query) throws DAOException {
        List<Test> tests = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(String.format(GET_SORTED, query));
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                tests.add(createTest(resultSet));
            }
        } catch (SQLException e) {
            logger.error(String.format("Couldn't get sorted list of tests because of %s", e.getMessage()));
            throw new DAOException(e);
        }

        return tests;
    }

    /**
     * Obtains number of all records matching filter
     * @param filter should contain 'where' to specify query
     * @return number of records
     * @throws DAOException is wrapper for SQLException
     */
    @Override
    public int getNumberOfRecords(String filter) throws DAOException {
        int numberOfRecords = 0;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(String.format(GET_NUMBER_OF_RECORDS, filter));
             ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                numberOfRecords = resultSet.getInt(NUMBER_OF_RECORDS);
            }
        } catch (SQLException e) {
            logger.error(String.format("Couldn't get number of tests because of %s", e.getMessage()));
            throw new DAOException(e);
        }

        return numberOfRecords;
    }

    /**
     * Obtains list of all tests from database
     * @return tests list
     * @throws DAOException is wrapper for SQLException
     */
    @Override
    public List<Test> getAll() throws DAOException {
        List<Test> tests = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_TESTS);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                tests.add(createTest(resultSet));
            }
        } catch (SQLException e) {
            logger.error(String.format("Couldn't get list of all tests because of %s", e.getMessage()));
            throw new DAOException(e);
        }
        return tests;
    }

    /**
     * Inserts new test to database
     * @param test concrete entity in implementations
     * @throws DAOException is wrapper for SQLException
     */
    @Override
    public void add(Test test) throws DAOException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(ADD_TEST, Statement.RETURN_GENERATED_KEYS)) {
            setStatementFieldsForAddMethod(test, preparedStatement);
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                test.setId(resultSet.getLong(1));
            }
        } catch (SQLException e) {
            logger.error(String.format("Couldn't add new test because of %s", e.getMessage()));
            throw new DAOException(e);
        }
    }

    /**
     * Updates test
     * @param test should contain all necessary fields
     * @throws DAOException is wrapper for SQLException
     */
    @Override
    public void update(Test test) throws DAOException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_TEST)) {
            setStatementFieldsForUpdateMethod(test, preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(String.format("Couldn't update test with id=%s because of %s", test.getId(), e.getMessage()));
            throw new DAOException(e);
        }
    }

    /**
     * Deletes test record from database
     * @param id value of id field in database
     * @throws DAOException is wrapper for SQLException
     */
    @Override
    public void delete(long id) throws DAOException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_TEST)) {
            int k = 0;
            preparedStatement.setLong(++k, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(String.format("Couldn't delete test with id=%d because of %s", id, e.getMessage()));
            throw new DAOException(e);
        }
    }

    /**
     * Creates test entity from result set
     * @param resultSet set that contains necessary data
     * @return Test entity
     * @throws SQLException if something go wrong
     */
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
