package com.my.testing.model.dao.mysql;

import com.my.testing.exceptions.DAOException;
import com.my.testing.model.dao.AnswerDAO;
import com.my.testing.model.entities.Answer;
import org.apache.logging.log4j.*;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;

import static com.my.testing.model.dao.mysql.constants.AnswerSQLQueries.*;
import static com.my.testing.model.dao.mysql.constants.SQLFields.*;

/**
 * Answer DAO class for MySQL database. Matches 'answer' table in database.
 *
 * @author Khelemendyk Dmytro
 * @version 1.0
 */
public class MysqlAnswerDAO implements AnswerDAO {
    private static final Logger logger = LogManager.getLogger(MysqlAnswerDAO.class);
    /** An instance of datasource to provide connection to database */
    private final DataSource dataSource;
    
    public MysqlAnswerDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Obtains instance of Answer from database by id
     * @param id value of id field in database
     * @return Optional.ofNullable answer is null if there is no answer
     * @throws DAOException is wrapper for SQLException
     */
    @Override
    public Optional<Answer> getById(long id) throws DAOException {
        Answer answer = null;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ANSWER_BY_ID)) {
            int k = 0;
            preparedStatement.setLong(++k, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    answer = createAnswer(resultSet);
                }
            }
        } catch (SQLException e) {
            logger.error(String.format("Couldn't find answer with id=%d because of %s", id, e.getMessage()));
            throw new DAOException(e);
        }

        return Optional.ofNullable(answer);
    }

    /**
     * Obtains list of all answers from database
     * @return answers list
     * @throws DAOException is wrapper for SQLException
     */
    @Override
    public List<Answer> getAll() throws DAOException {
        List<Answer> answers = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ANSWERS);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                answers.add(createAnswer(resultSet));
            }
        } catch (SQLException e) {
            logger.error(String.format("Couldn't get list of all answers because of %s", e.getMessage()));
            throw new DAOException(e);
        }

        return answers;
    }

    /**
     * Obtains list of all answers by question id from database
     * @param questionId value of question id in database
     * @return answers list
     * @throws DAOException is wrapper for SQLException
     */
    @Override
    public List<Answer> getAllByQuestionId(long questionId) throws DAOException {
        List<Answer> answers = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ANSWERS_BY_QUESTION_ID)) {
            int k = 0;
            preparedStatement.setLong(++k, questionId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    answers.add(createAnswer(resultSet));
                }
            }
        } catch (SQLException e) {
            logger.error(String.format("Couldn't get list of all answers by question id=%d because of %s", questionId, e.getMessage()));
            throw new DAOException(e);
        }

        return answers;
    }

    /**
     * Inserts new answer to database
     * @param answer concrete entity in implementations
     * @throws DAOException is wrapper for SQLException
     */
    @Override
    public void add(Answer answer) throws DAOException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(ADD_ANSWER)) {
            setStatementFieldsForAddMethod(answer, preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(String.format("Couldn't add new answer because of %s", e.getMessage()));
            throw new DAOException(e);
        }
    }

    /**
     * Updates answer
     * @param answer should contain all necessary fields
     * @throws DAOException is wrapper for SQLException
     */
    @Override
    public void update(Answer answer) throws DAOException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ANSWER)) {
            setStatementFieldsForUpdateMethod(answer, preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(String.format("Couldn't update answer with id=%d because of %s", answer.getId(), e.getMessage()));
            throw new DAOException(e);
        }
    }

    /**
     * Deletes answer record from database
     * @param id value of id field in database
     * @throws DAOException is wrapper for SQLException
     */
    @Override
    public void delete(long id) throws DAOException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ANSWER)) {
            int k = 0;
            preparedStatement.setLong(++k, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(String.format("Couldn't delete answer with id=%d because of %s", id, e.getMessage()));
            throw new DAOException(e);
        }
    }

    /**
     * Deletes all answers by question id
     * @param questionId value of question id in database
     * @throws DAOException is wrapper for SQLException
     */
    @Override
    public void deleteAllByQuestionId(long questionId) throws DAOException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ALL_BY_QUESTION_ID)) {
            int k = 0;
            preparedStatement.setLong(++k, questionId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(String.format("Couldn't delete answer by question id=%d because of %s", questionId, e.getMessage()));
            throw new DAOException(e);
        }
    }

    /**
     * Creates answer entity from result set
     * @param resultSet set that contains necessary data
     * @return Answer entity
     * @throws SQLException if something go wrong
     */
    private Answer createAnswer(ResultSet resultSet) throws SQLException {
        return Answer.builder()
                .id(resultSet.getLong(ID))
                .text(resultSet.getString(TEXT))
                .isCorrect(resultSet.getInt(IS_CORRECT) == 1)
                .questionId(resultSet.getLong(QUESTION_ID))
                .build();
    }

    private void setStatementFieldsForAddMethod(Answer answer, PreparedStatement preparedStatement) throws SQLException {
        int k = 0;
        preparedStatement.setString(++k, answer.getText());
        preparedStatement.setInt(++k, answer.isCorrect() ? 1 : 0);
        preparedStatement.setLong(++k, answer.getQuestionId());
    }

    private void setStatementFieldsForUpdateMethod(Answer answer, PreparedStatement preparedStatement) throws SQLException {
        int k = 0;
        preparedStatement.setString(++k, answer.getText());
        preparedStatement.setInt(++k, answer.isCorrect() ? 1 : 0);
        preparedStatement.setLong(++k, answer.getQuestionId());
        preparedStatement.setLong(++k, answer.getId());
    }
}
