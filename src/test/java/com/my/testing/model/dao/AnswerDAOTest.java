package com.my.testing.model.dao;

import com.my.testing.exceptions.DAOException;
import com.my.testing.model.dao.mysql.MysqlAnswerDAO;
import com.my.testing.model.entities.Answer;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.*;
import org.mockito.quality.Strictness;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

import static com.my.testing.Constants.*;
import static com.my.testing.model.dao.mysql.constants.SQLFields.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class AnswerDAOTest {
    private static Answer answerExample;
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
        answerExample = Answer.builder()
                .id(ID_VALUE)
                .text(TEXT_VALUE)
                .isCorrect(IS_CORRECT_VALUE)
                .questionId(ID_VALUE)
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
    void testGetById() throws SQLException, DAOException {
        AnswerDAO answerDAO = new MysqlAnswerDAO(dataSource);
        makeResultSetReturnValidAnswer();
        Answer resultAnswer = answerDAO.getById(ID_VALUE).orElse(null);
        assertNotNull(resultAnswer);
        assertEquals(answerExample, resultAnswer);
    }

    @Test
    void testGetByIdAbsent() throws SQLException, DAOException {
        AnswerDAO answerDAO = new MysqlAnswerDAO(dataSource);
        when(resultSet.next()).thenReturn(false);
        Answer resultAnswer = answerDAO.getById(ID_VALUE).orElse(null);
        assertNull(resultAnswer);
    }

    @Test
    void testSqlExceptionGetById() throws SQLException {
        AnswerDAO answerDAO = new MysqlAnswerDAO(dataSource);
        when(preparedStatement.executeQuery()).thenThrow(SQLException.class);
        assertThrows(DAOException.class, () -> answerDAO.getById(ID_VALUE));
    }

    @Test
    void testGetAll() throws SQLException, DAOException {
        AnswerDAO answerDAO = new MysqlAnswerDAO(dataSource);
        makeResultSetReturnValidAnswer();
        List<Answer> answers = answerDAO.getAll();
        assertEquals(ONE, answers.size());
        assertEquals(answerExample, answers.get(0));
    }

    @Test
    void testGetNoAnswers() throws SQLException, DAOException {
        AnswerDAO answerDAO = new MysqlAnswerDAO(dataSource);
        when(resultSet.next()).thenReturn(false);
        List<Answer> answers = answerDAO.getAll();
        assertEquals(ZERO, answers.size());
    }

    @Test
    void testSqlExceptionGetAll() throws SQLException {
        AnswerDAO answerDAO = new MysqlAnswerDAO(dataSource);
        when(preparedStatement.executeQuery()).thenThrow(SQLException.class);
        assertThrows(DAOException.class, answerDAO::getAll);
    }

    @Test
    void testGetAllByQuestionId() throws SQLException, DAOException {
        AnswerDAO answerDAO = new MysqlAnswerDAO(dataSource);
        makeResultSetReturnValidAnswer();
        List<Answer> answers = answerDAO.getAllByQuestionId(ID_VALUE);
        assertEquals(ONE, answers.size());
        assertEquals(answerExample, answers.get(0));
    }

    @Test
    void testGetAllByQuestionIdAbsent() throws SQLException, DAOException {
        AnswerDAO answerDAO = new MysqlAnswerDAO(dataSource);
        when(resultSet.next()).thenReturn(false);
        List<Answer> answers = answerDAO.getAllByQuestionId(ID_VALUE);
        assertEquals(ZERO, answers.size());
    }

    @Test
    void testSqlExceptionGetAllByQuestionId() throws SQLException {
        AnswerDAO answerDAO = new MysqlAnswerDAO(dataSource);
        when(preparedStatement.executeQuery()).thenThrow(SQLException.class);
        assertThrows(DAOException.class, () -> answerDAO.getAllByQuestionId(ID_VALUE));
    }

    @Test
    void testAdd() {
        AnswerDAO answerDAO = new MysqlAnswerDAO(dataSource);
        assertDoesNotThrow(() -> answerDAO.add(answerExample));
    }

    @Test
    void testSqlExceptionAdd() throws SQLException {
        AnswerDAO answerDAO = new MysqlAnswerDAO(dataSource);
        when(preparedStatement.executeUpdate()).thenThrow(SQLException.class);
        assertThrows(DAOException.class, () -> answerDAO.add(answerExample));
    }

    @Test
    void testUpdate() {
        AnswerDAO answerDAO = new MysqlAnswerDAO(dataSource);
        assertDoesNotThrow(() -> answerDAO.update(answerExample));
    }

    @Test
    void testSqlExceptionUpdate() throws SQLException {
        AnswerDAO answerDAO = new MysqlAnswerDAO(dataSource);
        when(preparedStatement.executeUpdate()).thenThrow(SQLException.class);
        assertThrows(DAOException.class, () -> answerDAO.update(answerExample));
    }

    @Test
    void testDelete() {
        AnswerDAO answerDAO = new MysqlAnswerDAO(dataSource);
        assertDoesNotThrow(() -> answerDAO.delete(ID_VALUE));
    }

    @Test
    void testSqlExceptionDelete() throws SQLException {
        AnswerDAO answerDAO = new MysqlAnswerDAO(dataSource);
        when(preparedStatement.executeUpdate()).thenThrow(SQLException.class);
        assertThrows(DAOException.class, () -> answerDAO.delete(ID_VALUE));
    }

    @Test
    void testDeleteAllByQuestionId() {
        AnswerDAO answerDAO = new MysqlAnswerDAO(dataSource);
        assertDoesNotThrow(() -> answerDAO.deleteAllByQuestionId(ID_VALUE));
    }

    @Test
    void testSqlExceptionDeleteAllByQuestionId() throws SQLException {
        AnswerDAO answerDAO = new MysqlAnswerDAO(dataSource);
        when(preparedStatement.executeUpdate()).thenThrow(SQLException.class);
        assertThrows(DAOException.class, () -> answerDAO.deleteAllByQuestionId(ID_VALUE));
    }

    private void makeResultSetReturnValidAnswer() throws SQLException {
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getLong(ID)).thenReturn(ID_VALUE);
        when(resultSet.getString(TEXT)).thenReturn(TEXT_VALUE);
        when(resultSet.getInt(IS_CORRECT)).thenReturn(0);
        when(resultSet.getLong(QUESTION_ID)).thenReturn(ID_VALUE);
    }
}
