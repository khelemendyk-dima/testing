package com.my.testing.model.dao;

import com.my.testing.exceptions.DAOException;
import com.my.testing.model.dao.mysql.MysqlQuestionDAO;
import com.my.testing.model.entities.Question;
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
class QuestionDAOTest {
    private static Question questionExample;
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
        questionExample = Question.builder()
                .id(ID_VALUE)
                .text(TEXT_VALUE)
                .testId(ID_VALUE)
                .build();
    }

    @BeforeEach
    void initEach() throws SQLException {
        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(connection.prepareStatement(anyString(), eq(Statement.RETURN_GENERATED_KEYS)))
                .thenReturn(preparedStatement);
        doNothing().when(preparedStatement).setInt(anyInt(), anyInt());
        doNothing().when(preparedStatement).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement).setString(anyInt(), anyString());
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(preparedStatement.getGeneratedKeys()).thenReturn(resultSet);
    }

    @Test
    void testGetById() throws SQLException, DAOException {
        QuestionDAO questionDAO = new MysqlQuestionDAO(dataSource);
        makeResultSetReturnValidQuestion();
        Question resultQuestion = questionDAO.getById(ID_VALUE).orElse(null);
        assertNotNull(resultQuestion);
        assertEquals(questionExample, resultQuestion);
    }

    @Test
    void testGetByIdAbsent() throws SQLException, DAOException {
        QuestionDAO questionDAO = new MysqlQuestionDAO(dataSource);
        when(resultSet.next()).thenReturn(false);
        Question resultQuestion = questionDAO.getById(ID_VALUE).orElse(null);
        assertNull(resultQuestion);
    }

    @Test
    void testSqlExceptionGetById() throws SQLException {
        QuestionDAO questionDAO = new MysqlQuestionDAO(dataSource);
        when(preparedStatement.executeQuery()).thenThrow(SQLException.class);
        assertThrows(DAOException.class, () -> questionDAO.getById(ID_VALUE));
    }

    @Test
    void testGetAll() throws SQLException, DAOException {
        QuestionDAO questionDAO = new MysqlQuestionDAO(dataSource);
        makeResultSetReturnValidQuestion();
        List<Question> questions = questionDAO.getAll();
        assertEquals(ONE, questions.size());
        assertEquals(questionExample, questions.get(0));
    }

    @Test
    void testGetNoQuestions() throws SQLException, DAOException {
        QuestionDAO questionDAO = new MysqlQuestionDAO(dataSource);
        when(resultSet.next()).thenReturn(false);
        List<Question> questions = questionDAO.getAll();
        assertEquals(ZERO, questions.size());
    }

    @Test
    void testSqlExceptionGetAll() throws SQLException {
        QuestionDAO questionDAO = new MysqlQuestionDAO(dataSource);
        when(preparedStatement.executeQuery()).thenThrow(SQLException.class);
        assertThrows(DAOException.class, questionDAO::getAll);
    }

    @Test
    void testGetAllByTestId() throws SQLException, DAOException {
        QuestionDAO questionDAO = new MysqlQuestionDAO(dataSource);
        makeResultSetReturnValidQuestion();
        List<Question> questions = questionDAO.getAllByTestId(ID_VALUE);
        assertEquals(ONE, questions.size());
        assertEquals(questionExample, questions.get(0));
    }

    @Test
    void testGetAllByTestIdAbsent() throws SQLException, DAOException {
        QuestionDAO questionDAO = new MysqlQuestionDAO(dataSource);
        when(resultSet.next()).thenReturn(false);
        List<Question> questions = questionDAO.getAllByTestId(ID_VALUE);
        assertEquals(ZERO, questions.size());
    }

    @Test
    void testSqlExceptionGetAllByTestId() throws SQLException {
        QuestionDAO questionDAO = new MysqlQuestionDAO(dataSource);
        when(preparedStatement.executeQuery()).thenThrow(SQLException.class);
        assertThrows(DAOException.class, () -> questionDAO.getAllByTestId(ID_VALUE));
    }

    @Test
    void testAdd() {
        QuestionDAO questionDAO = new MysqlQuestionDAO(dataSource);
        assertDoesNotThrow(() -> questionDAO.add(questionExample));
    }

    @Test
    void testSqlExceptionAdd() throws SQLException {
        QuestionDAO questionDAO = new MysqlQuestionDAO(dataSource);
        when(preparedStatement.executeUpdate()).thenThrow(SQLException.class);
        assertThrows(DAOException.class, () -> questionDAO.add(questionExample));
    }

    @Test
    void testUpdate() {
        QuestionDAO questionDAO = new MysqlQuestionDAO(dataSource);
        assertDoesNotThrow(() -> questionDAO.update(questionExample));
    }

    @Test
    void testSqlExceptionUpdate() throws SQLException {
        QuestionDAO questionDAO = new MysqlQuestionDAO(dataSource);
        when(preparedStatement.executeUpdate()).thenThrow(SQLException.class);
        assertThrows(DAOException.class, () -> questionDAO.update(questionExample));
    }

    @Test
    void testDelete() {
        QuestionDAO questionDAO = new MysqlQuestionDAO(dataSource);
        assertDoesNotThrow(() -> questionDAO.delete(ID_VALUE));
    }

    @Test
    void testSqlExceptionDelete() throws SQLException {
        QuestionDAO questionDAO = new MysqlQuestionDAO(dataSource);
        when(preparedStatement.executeUpdate()).thenThrow(SQLException.class);
        assertThrows(DAOException.class, () -> questionDAO.delete(ID_VALUE));
    }
    private void makeResultSetReturnValidQuestion() throws SQLException {
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getLong(ID)).thenReturn(ID_VALUE);
        when(resultSet.getString(TEXT)).thenReturn(TEXT_VALUE);
        when(resultSet.getLong(TEST_ID)).thenReturn(ID_VALUE);
    }
}
