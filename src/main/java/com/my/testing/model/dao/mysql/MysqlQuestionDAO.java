package com.my.testing.model.dao.mysql;

import com.my.testing.exceptions.DAOException;
import com.my.testing.model.dao.QuestionDAO;
import com.my.testing.model.entities.Question;
import org.apache.logging.log4j.*;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;

import static com.my.testing.model.dao.mysql.constants.QuestionSQLQueries.*;
import static com.my.testing.model.dao.mysql.constants.SQLFields.*;

/**
 * Question DAO class for MySQL database. Matches 'question' table in database.
 *
 * @author Khelemendyk Dmytro
 * @version 1.0
 */
public class MysqlQuestionDAO implements QuestionDAO {
    private static final Logger logger = LogManager.getLogger(MysqlQuestionDAO.class);
    /** An instance of datasource to provide connection to database */
    private final DataSource dataSource;

    public MysqlQuestionDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Obtains instance of Question from database by id
     * @param id value of id field in database
     * @return Optional.ofNullable question is null if there is no question
     * @throws DAOException is wrapper for SQLException
     */
    @Override
    public Optional<Question> getById(long id) throws DAOException {
        Question question = null;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_QUESTION_BY_ID)) {
            int k = 0;
            preparedStatement.setLong(++k, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    question = createQuestion(resultSet);
                }
            }
        } catch (SQLException e) {
            logger.error(String.format("Couldn't find question with id=%d because of %s", id, e.getMessage()));
            throw new DAOException(e);
        }

        return Optional.ofNullable(question);
    }

    /**
     * Obtains list of all questions from database
     * @return questions list
     * @throws DAOException is wrapper for SQLException
     */
    @Override
    public List<Question> getAll() throws DAOException {
        List<Question> questions = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_QUESTIONS);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                questions.add(createQuestion(resultSet));
            }
        } catch (SQLException e) {
            logger.error(String.format("Couldn't get list of all questions because of %s", e.getMessage()));
            throw new DAOException(e);
        }

        return questions;
    }

    /**
     * Obtains list of all questions by test id from database
     * @param testId value of test id in database
     * @return questions list
     * @throws DAOException is wrapper for SQLException
     */
    @Override
    public List<Question> getAllByTestId(long testId) throws DAOException {
        List<Question> questions = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_QUESTIONS_BY_TEST_ID)) {
            int k = 0;
            preparedStatement.setLong(++k, testId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    questions.add(createQuestion(resultSet));
                }
            }
        } catch (SQLException e) {
            logger.error(String.format("Couldn't get list of all questions by test with id=%d because of %s", testId, e.getMessage()));
            throw new DAOException(e);
        }

        return questions;
    }

    /**
     * Inserts new question to database
     * @param question concrete entity in implementations
     * @throws DAOException is wrapper for SQLException
     */
    @Override
    public void add(Question question) throws DAOException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(ADD_QUESTION, Statement.RETURN_GENERATED_KEYS)) {
            setStatementFieldsForAddMethod(question, preparedStatement);
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                question.setId(resultSet.getLong(1));
            }
        } catch (SQLException e) {
            logger.error(String.format("Couldn't add new question because of %s", e.getMessage()));
            throw new DAOException(e);
        }
    }

    /**
     * Updates question
     * @param question should contain all necessary fields
     * @throws DAOException is wrapper for SQLException
     */
    @Override
    public void update(Question question) throws DAOException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_QUESTION)) {
            setStatementFieldsForUpdateMethod(question, preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(String.format("Couldn't update question with id=%d because of %s", question.getId(), e.getMessage()));
            throw new DAOException(e);
        }
    }

    /**
     * Deletes question record from database
     * @param id value of id field in database
     * @throws DAOException is wrapper for SQLException
     */
    @Override
    public void delete(long id) throws DAOException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_QUESTION)) {
            int k = 0;
            preparedStatement.setLong(++k, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(String.format("Couldn't delete question with id=%d because of %s", id, e.getMessage()));
            throw new DAOException(e);
        }
    }

    /**
     * Creates question from result set
     * @param resultSet set that contains necessary data
     * @return Question entity
     * @throws SQLException if something go wrong
     */
    private Question createQuestion(ResultSet resultSet) throws SQLException {
        return Question.builder()
                .id(resultSet.getLong(ID))
                .text(resultSet.getString(TEXT))
                .testId(resultSet.getLong(TEST_ID))
                .build();
    }

    private void setStatementFieldsForAddMethod(Question question, PreparedStatement preparedStatement) throws SQLException {
        int k = 0;
        preparedStatement.setString(++k, question.getText());
        preparedStatement.setLong(++k, question.getTestId());
    }

    private void setStatementFieldsForUpdateMethod(Question question, PreparedStatement preparedStatement) throws SQLException {
        int k = 0;
        preparedStatement.setString(++k, question.getText());
        preparedStatement.setLong(++k, question.getTestId());
        preparedStatement.setLong(++k, question.getId());
    }
}
