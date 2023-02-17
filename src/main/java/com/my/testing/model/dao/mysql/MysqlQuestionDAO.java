package com.my.testing.model.dao.mysql;

import com.my.testing.exceptions.DAOException;
import com.my.testing.model.dao.QuestionDAO;
import com.my.testing.model.entities.Question;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;

import static com.my.testing.model.dao.mysql.constants.QuestionSQLQueries.*;
import static com.my.testing.model.dao.mysql.constants.SQLFields.*;

public class MysqlQuestionDAO implements QuestionDAO {
    private final DataSource dataSource;

    public MysqlQuestionDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }
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
            throw new DAOException(e);
        }

        return Optional.ofNullable(question);
    }

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
            throw new DAOException(e);
        }

        return questions;
    }

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
            throw new DAOException(e);
        }

        return questions;
    }

    @SuppressWarnings("DuplicatedCode")
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
            throw new DAOException(e);
        }
    }

    @Override
    public void update(Question question) throws DAOException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_QUESTION)) {
            setStatementFieldsForUpdateMethod(question, preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void delete(long id) throws DAOException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_QUESTION)) {
            int k = 0;
            preparedStatement.setLong(++k, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

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
