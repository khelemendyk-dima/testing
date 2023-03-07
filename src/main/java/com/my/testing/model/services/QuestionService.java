package com.my.testing.model.services;

import com.my.testing.dto.QuestionDTO;
import com.my.testing.exceptions.ServiceException;

import java.util.List;

/**
 * QuestionService interface.
 * Implements all methods in concrete QuestionService
 *
 * @author Khelemendyk Dmytro
 * @version 1.0
 */
public interface QuestionService extends Service<QuestionDTO> {

    /**
     * Calls DAO to add new entity
     * @param questionDTO DTO to be added as entity to database
     * @throws ServiceException - may wrap DAOException or be thrown by another mistakes
     */
    void add(QuestionDTO questionDTO) throws ServiceException;

    /**
     * Obtains list of all questions by test id
     * @param testId id as a String to validate and convert to long
     * @return list of questionDTOs
     * @throws ServiceException - may wrap DAOException or be thrown by another mistakes
     */
    List<QuestionDTO> getAllByTestId(String testId) throws ServiceException;

}
