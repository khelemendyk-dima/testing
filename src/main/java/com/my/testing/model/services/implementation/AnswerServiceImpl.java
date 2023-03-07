package com.my.testing.model.services.implementation;

import com.my.testing.dto.AnswerDTO;
import com.my.testing.exceptions.*;
import com.my.testing.model.dao.AnswerDAO;
import com.my.testing.model.entities.Answer;
import com.my.testing.model.services.AnswerService;

import java.util.*;

import static com.my.testing.utils.ConvertorUtil.*;
import static com.my.testing.utils.ValidatorUtil.*;

/**
 * Implementation of AnswerService interface.
 * Contains answerDAO field to work with AnswerDAO
 *
 * @author Khelemendyk Dmytro
 * @version 1.0
 */
public class AnswerServiceImpl implements AnswerService {
    private final AnswerDAO answerDAO;

    /**
     * @param answerDAO DAO to work with database
     */
    public AnswerServiceImpl(AnswerDAO answerDAO) {
        this.answerDAO = answerDAO;
    }

    /**
     * Gets AnswerDTO from action and calls DAO to add new entity. Validates answer's text.
     * Converts AnswerDTO to Answer
     * @param answerDTO DTO to be added as entity to database
     * @throws ServiceException - may wrap DAOException or be thrown as IncorrectFormatException with specific message
     */
    @Override
    public void add(AnswerDTO answerDTO) throws ServiceException {
        validateAnswerText(answerDTO.getText());
        Answer answer = convertDTOToAnswer(answerDTO);
        try {
            answerDAO.add(answer);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Obtains instance of Answer from DAO by id. Checks if id valid. Converts Answer to AnswerDTO
     * @param idString id as a String to validate and convert to long
     * @return AnswerDTO instance
     * @throws ServiceException - may wrap DAOException or be thrown as NoSuchAnswerException
     */
    @Override
    public AnswerDTO getById(String idString) throws ServiceException {
        AnswerDTO answerDTO;
        long answerId = getAnswerId(idString);
        try {
            Answer answer = answerDAO.getById(answerId).orElseThrow(NoSuchAnswerException::new);
            answerDTO = convertAnswerToDTO(answer);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }

        return answerDTO;
    }

    /**
     * Obtains list of all instances of Answer from DAO. Converts Answers to AnswerDTOs
     * @return list of AnswerDTOs
     * @throws ServiceException - may wrap DAOException
     */
    @Override
    public List<AnswerDTO> getAll() throws ServiceException {
        List<AnswerDTO> answerDTOS = new ArrayList<>();

        try {
            List<Answer> answers = answerDAO.getAll();
            answers.forEach(answer -> answerDTOS.add(convertAnswerToDTO(answer)));
        } catch (DAOException e) {
            throw new ServiceException(e);
        }

        return answerDTOS;
    }

    /**
     * Obtains list of all instances of Answer by question id from DAO. Validates question's id.
     * Converts Answers to AnswerDTOs
     * @param questionId id as a String to validate and convert to long
     * @return list of AnswerDTOs
     * @throws ServiceException - may wrap DAOException or be thrown NoSuchQuestionException
     */
    @Override
    public List<AnswerDTO> getAllByQuestionId(String questionId) throws ServiceException {
        List<AnswerDTO> answerDTOS = new ArrayList<>();
        long id = getQuestionId(questionId);

        try {
            List<Answer> answers = answerDAO.getAllByQuestionId(id);
            answers.forEach(answer -> answerDTOS.add(convertAnswerToDTO(answer)));
        } catch (DAOException e) {
            throw new ServiceException(e);
        }

        return answerDTOS;
    }

    /**
     * Updates Answer's text, isCorrect, questionId. Validates answer's text.
     * Converts AnswerDTO to Answer
     * @param entity DTO to be updated
     * @throws ServiceException - may wrap DAOException or be thrown as IncorrectFormatException
     */
    @Override
    public void update(AnswerDTO entity) throws ServiceException {
        validateAnswerText(entity.getText());
        try {
            answerDAO.update(convertDTOToAnswer(entity));
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Deletes Answer entity from database. Validates id
     * @param idString id as a String to validate and convert to long
     * @throws ServiceException - may wrap DAOException or be thrown as NoSuchAnswerException
     */
    @Override
    public void delete(String idString) throws ServiceException {
        long answerId = getAnswerId(idString);

        try {
            answerDAO.delete(answerId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Deletes all answers by question id from database. Validates question's id.
     * @param questionId id as a String to validate and convert to long
     * @throws ServiceException - may wrap DAOException or be thrown as NoSuchQuestionException
     */
    @Override
    public void deleteAllByQuestionId(String questionId) throws ServiceException {
        long id = getQuestionId(questionId);

        try {
            answerDAO.deleteAllByQuestionId(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
