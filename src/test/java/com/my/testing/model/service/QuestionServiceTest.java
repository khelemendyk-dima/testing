package com.my.testing.model.service;

import com.my.testing.dto.QuestionDTO;
import com.my.testing.exceptions.*;
import com.my.testing.model.dao.QuestionDAO;
import com.my.testing.model.entities.Question;
import com.my.testing.model.services.QuestionService;
import com.my.testing.model.services.implementation.QuestionServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.util.*;

import static com.my.testing.Constants.*;
import static com.my.testing.exceptions.constants.Message.ENTER_CORRECT_TEXT;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class QuestionServiceTest {
    private final QuestionDAO questionDAO = mock(QuestionDAO.class);
    private final QuestionService questionService = new QuestionServiceImpl(questionDAO);

    @Test
    void testAdd() throws DAOException {
        doNothing().when(questionDAO).add(any());
        assertDoesNotThrow(() -> questionService.add(getQuestionDTO()));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void testAddIncorrectText(String text) throws DAOException {
        doNothing().when(questionDAO).add(any());
        QuestionDTO questionDTO = getQuestionDTO();
        questionDTO.setText(text);
        IncorrectFormatException e = assertThrows(IncorrectFormatException.class,
                () -> questionService.add(questionDTO));
        assertEquals(ENTER_CORRECT_TEXT, e.getMessage());
    }

    @Test
    void testGetAllByTestId() throws DAOException, ServiceException {
        when(questionDAO.getAllByTestId(ID_VALUE)).thenReturn(List.of(getQuestion()));
        assertIterableEquals(List.of(getQuestionDTO()), questionService.getAllByTestId(String.valueOf(ID_VALUE)));
    }

    @Test
    void testSQLErrorGetAllByTestId() throws DAOException {
        doThrow(DAOException.class).when(questionDAO).getAllByTestId(anyLong());
        assertThrows(ServiceException.class, () -> questionService.getAllByTestId(String.valueOf(ID_VALUE)));
    }

    @Test
    void testGetById() throws DAOException, ServiceException {
        when(questionDAO.getById(ID_VALUE)).thenReturn(Optional.of(getQuestion()));
        assertEquals(getQuestionDTO(), questionService.getById(String.valueOf(ID_VALUE)));
    }

    @Test
    void testSQLErrorGetById() throws DAOException {
        doThrow(DAOException.class).when(questionDAO).getById(anyLong());
        assertThrows(ServiceException.class, () -> questionService.getById(String.valueOf(ID_VALUE)));
    }

    @Test
    void testGetByIdNoQuestion() throws DAOException {
        when(questionDAO.getById(ID_VALUE)).thenReturn(Optional.empty());
        assertThrows(NoSuchQuestionException.class, () -> questionService.getById(String.valueOf(ID_VALUE)));
    }

    @Test
    void testGetAll() throws DAOException, ServiceException {
        when(questionDAO.getAll()).thenReturn(List.of(getQuestion()));
        assertIterableEquals(List.of(getQuestionDTO()), questionService.getAll());
    }

    @Test
    void testSQLErrorGetAll() throws DAOException {
        doThrow(DAOException.class).when(questionDAO).getAll();
        assertThrows(ServiceException.class, questionService::getAll);
    }

    @Test
    void testUpdate() throws DAOException {
        doNothing().when(questionDAO).update(any());
        assertDoesNotThrow(() -> questionService.update(getQuestionDTO()));
    }

    @Test
    void testSQLErrorUpdate() throws DAOException {
        doThrow(DAOException.class).when(questionDAO).update(any());
        assertThrows(ServiceException.class, () -> questionService.update(getQuestionDTO()));
    }

    @Test
    void testDelete() throws DAOException {
        doNothing().when(questionDAO).delete(anyLong());
        assertDoesNotThrow(() -> questionService.delete(String.valueOf(ID_VALUE)));
    }

    @Test
    void testSQLErrorDelete() throws DAOException {
        doThrow(DAOException.class).when(questionDAO).delete(anyLong());
        assertThrows(ServiceException.class, () -> questionService.delete(String.valueOf(ID_VALUE)));
    }

    private QuestionDTO getQuestionDTO() {
        return QuestionDTO.builder()
                .id(ID_VALUE)
                .text(TEXT_VALUE)
                .testId(ID_VALUE)
                .build();
    }

    private Question getQuestion() {
        return Question.builder()
                .id(ID_VALUE)
                .text(TEXT_VALUE)
                .testId(ID_VALUE)
                .build();
    }
}
