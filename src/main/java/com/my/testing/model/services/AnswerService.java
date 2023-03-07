package com.my.testing.model.services;

import com.my.testing.dto.AnswerDTO;
import com.my.testing.exceptions.ServiceException;

import java.util.List;

/**
 * AnswerService interface.
 * Implements all methods in concrete AnswerService
 *
 * @author Khelemendyk Dmytro
 * @version 1.0
 */
public interface AnswerService extends Service<AnswerDTO> {

    /**
     * Calls DAO to add new entity
     * @param answerDTO DTO to be added as entity to database
     * @throws ServiceException - may wrap DAOException or be thrown by another mistakes
     */
    void add(AnswerDTO answerDTO) throws ServiceException;

    /**
     * Obtains list of all answers by question id from DAO
     * @param questionId id as a String to validate and convert to long
     * @return list of answerDTOs
     * @throws ServiceException - may wrap DAOException or be thrown by another mistakes
     */
    List<AnswerDTO> getAllByQuestionId(String questionId) throws ServiceException;

    /**
     * Deletes all answers by question id
     * @param questionId id as a String to validate and convert to long
     * @throws ServiceException - may wrap DAOException or be thrown by another mistakes
     */
    void deleteAllByQuestionId(String questionId) throws ServiceException;
}
