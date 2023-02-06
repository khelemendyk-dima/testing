package com.my.testing.model.dao.mysql;

import com.my.testing.exceptions.DAOException;
import com.my.testing.model.connection.DataSource;
import com.my.testing.model.dao.AnswerDAO;
import com.my.testing.model.entities.Answer;

import java.sql.*;
import java.util.*;

import static com.my.testing.model.dao.mysql.constants.AnswerSQLQueries.*;
import static com.my.testing.model.dao.mysql.constants.SQLFields.*;

public class MysqlAnswerDAO implements AnswerDAO {
    @Override
    public Optional<Answer> getById(long id) throws DAOException {
        Answer answer = null;
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ANSWER_BY_ID)) {
            int k = 0;
            preparedStatement.setLong(++k, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    answer = createAnswer(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }

        return Optional.ofNullable(answer);
    }

    @Override
    public List<Answer> getAll() throws DAOException {
        List<Answer> answers = new ArrayList<>();
        try (Connection connection = DataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(GET_ANSWERS)) {
            while (resultSet.next()) {
                answers.add(createAnswer(resultSet));
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }

        return answers;
    }

    @Override
    public List<Answer> getAllByQuestionId(long questionId) throws DAOException {
        List<Answer> answers = new ArrayList<>();
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ANSWERS_BY_QUESTION_ID)) {
            int k = 0;
            preparedStatement.setLong(++k, questionId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    answers.add(createAnswer(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }

        return answers;
    }


    @Override
    public void add(Answer answer) throws DAOException {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(ADD_ANSWER)) {
            setStatementFieldsForAddMethod(answer, preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void update(Answer answer) throws DAOException {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ANSWER)) {
            setStatementFieldsForUpdateMethod(answer, preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void delete(long id) throws DAOException {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ANSWER)) {
            int k = 0;
            preparedStatement.setLong(++k, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void deleteAllByQuestionId(long questionId) throws DAOException {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ALL_BY_QUESTION_ID)) {
            int k = 0;
            preparedStatement.setLong(++k, questionId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

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
