package com.my.testing.model.service;

import com.my.testing.dto.AnswerDTO;
import com.my.testing.exceptions.*;
import com.my.testing.model.dao.AnswerDAO;
import com.my.testing.model.entities.Answer;
import com.my.testing.model.services.AnswerService;
import com.my.testing.model.services.implementation.AnswerServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.util.*;

import static com.my.testing.Constants.*;
import static com.my.testing.exceptions.constants.Message.ENTER_CORRECT_TEXT;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class AnswerServiceTest {
    private final AnswerDAO answerDAO = mock(AnswerDAO.class);
    private final AnswerService answerService = new AnswerServiceImpl(answerDAO);

    @Test
    void testAdd() throws DAOException {
        doNothing().when(answerDAO).add(any());
        assertDoesNotThrow(() -> answerService.add(getAnswerDTO()));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void testAddIncorrectText(String text) throws DAOException {
        doNothing().when(answerDAO).add(any());
        AnswerDTO answerDTO = getAnswerDTO();
        answerDTO.setText(text);
        IncorrectFormatException e = assertThrows(IncorrectFormatException.class,
                () -> answerService.add(answerDTO));
        assertEquals(ENTER_CORRECT_TEXT, e.getMessage());
    }

    @Test
    void testGetAllByQuestionId() throws DAOException, ServiceException {
        when(answerDAO.getAllByQuestionId(ID_VALUE)).thenReturn(List.of(getAnswer()));
        assertIterableEquals(List.of(getAnswerDTO()), answerService.getAllByQuestionId(String.valueOf(ID_VALUE)));
    }

    @Test
    void testSQLErrorGetAllByQuestionId() throws DAOException {
        doThrow(DAOException.class).when(answerDAO).getAllByQuestionId(anyLong());
        assertThrows(ServiceException.class, () -> answerService.getAllByQuestionId(String.valueOf(ID_VALUE)));
    }

    @Test
    void testGetById() throws DAOException, ServiceException {
        when(answerDAO.getById(ID_VALUE)).thenReturn(Optional.of(getAnswer()));
        assertEquals(getAnswerDTO(), answerService.getById(String.valueOf(ID_VALUE)));
    }

    @Test
    void testSQLErrorGetById() throws DAOException {
        doThrow(DAOException.class).when(answerDAO).getById(anyLong());
        assertThrows(ServiceException.class, () -> answerService.getById(String.valueOf(ID_VALUE)));
    }

    @Test
    void testGetByIdNoAnswer() throws DAOException {
        when(answerDAO.getById(ID_VALUE)).thenReturn(Optional.empty());
        assertThrows(NoSuchAnswerException.class, () -> answerService.getById(String.valueOf(ID_VALUE)));
    }

    @Test
    void testGetAll() throws DAOException, ServiceException {
        when(answerDAO.getAll()).thenReturn(List.of(getAnswer()));
        assertIterableEquals(List.of(getAnswerDTO()), answerService.getAll());
    }

    @Test
    void testSQLErrorGetAll() throws DAOException {
        doThrow(DAOException.class).when(answerDAO).getAll();
        assertThrows(ServiceException.class, answerService::getAll);
    }

    @Test
    void testUpdate() throws DAOException {
        doNothing().when(answerDAO).update(any());
        assertDoesNotThrow(() -> answerService.update(getAnswerDTO()));
    }

    @Test
    void testSQLErrorUpdate() throws DAOException {
        doThrow(DAOException.class).when(answerDAO).update(any());
        assertThrows(ServiceException.class, () -> answerService.update(getAnswerDTO()));
    }

    @Test
    void testDelete() throws DAOException {
        doNothing().when(answerDAO).delete(anyLong());
        assertDoesNotThrow(() -> answerService.delete(String.valueOf(ID_VALUE)));
    }

    @Test
    void testSQLErrorDelete() throws DAOException {
        doThrow(DAOException.class).when(answerDAO).delete(anyLong());
        assertThrows(ServiceException.class, () -> answerService.delete(String.valueOf(ID_VALUE)));
    }

    @Test
    void testDeleteAllByQuestionId() throws DAOException {
        doNothing().when(answerDAO).deleteAllByQuestionId(ID_VALUE);
        assertDoesNotThrow(() -> answerService.deleteAllByQuestionId(String.valueOf(ID_VALUE)));
    }

    @Test
    void testSQLErrorDeleteAllByQuestionId() throws DAOException {
        doThrow(DAOException.class).when(answerDAO).deleteAllByQuestionId(anyLong());
        assertThrows(ServiceException.class, () -> answerService.deleteAllByQuestionId(String.valueOf(ID_VALUE)));
    }

    private AnswerDTO getAnswerDTO() {
        return AnswerDTO.builder()
                .id(ID_VALUE)
                .text(TEXT_VALUE)
                .isCorrect(IS_CORRECT_VALUE)
                .questionId(ID_VALUE)
                .build();
    }

    private Answer getAnswer() {
        return Answer.builder()
                .id(ID_VALUE)
                .text(TEXT_VALUE)
                .isCorrect(IS_CORRECT_VALUE)
                .questionId(ID_VALUE)
                .build();
    }
}
