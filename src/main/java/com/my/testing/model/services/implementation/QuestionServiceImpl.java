package com.my.testing.model.services.implementation;

import com.my.testing.dto.QuestionDTO;
import com.my.testing.exceptions.*;
import com.my.testing.model.dao.QuestionDAO;
import com.my.testing.model.entities.Question;
import com.my.testing.model.services.QuestionService;

import java.util.*;

import static com.my.testing.utils.ConvertorUtil.*;
import static com.my.testing.utils.ValidatorUtil.*;

/**
 * Implementation of QuestionService interface.
 * Contains questionDAO field to work with QuestionDAO
 *
 * @author Khelemendyk Dmytro
 * @version 1.0
 */
public class QuestionServiceImpl implements QuestionService {
    private final QuestionDAO questionDAO;

    /**
     * @param questionDAO DAO to work with database
     */
    public QuestionServiceImpl(QuestionDAO questionDAO) {
        this.questionDAO = questionDAO;
    }

    /**
     * Gets QuestionDTO from action and calls DAO to add new entity. Validates question's text.
     * Converts QuestionDTO to Question
     * @param questionDTO DTO to be added as entity to database
     * @throws ServiceException - may wrap DAOException or be thrown as IncorrectFormatException with specific message
     */
    @Override
    public void add(QuestionDTO questionDTO) throws ServiceException {
        validateQuestionText(questionDTO.getText());
        Question question = convertDTOToQuestion(questionDTO);
        try {
            questionDAO.add(question);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        questionDTO.setId(question.getId());
    }

    /**
     * Obtains instance of Question from DAO by id. Checks if id valid. Converts Question to QuestionDTO
     * @param idString id as a String to validate and convert to long
     * @return QuestionDTO instance
     * @throws ServiceException - may wrap DAOException or be thrown as NoSuchQuestionException
     */
    @Override
    public QuestionDTO getById(String idString) throws ServiceException {
        QuestionDTO questionDTO;
        long questionId = getQuestionId(idString);
        try {
            Question question = questionDAO.getById(questionId).orElseThrow(NoSuchQuestionException::new);
            questionDTO = convertQuestionToDTO(question);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }

        return questionDTO;
    }

    /**
     * Obtains list of all instances of Question from DAO. Converts Questions to QuestionDTOs
     * @return list of QuestionDTOs
     * @throws ServiceException - may wrap DAOException or be thrown as NoSuchQuestionException
     */
    @Override
    public List<QuestionDTO> getAll() throws ServiceException {
        List<QuestionDTO> questionDTOS = new ArrayList<>();

        try {
            List<Question> questions = questionDAO.getAll();
            questions.forEach(question -> questionDTOS.add(convertQuestionToDTO(question)));
        } catch (DAOException e) {
            throw new ServiceException(e);
        }

        return questionDTOS;
    }

    /**
     * Obtains list of all instances of Question by test id from DAO. Validates test's id.
     * Converts Questions to QuestionDTOs
     * @param testId id as a String to validate and convert to long
     * @return list of QuestionDTOs
     * @throws ServiceException - may wrap DAOException or be thrown as NoSuchTestException
     */
    @Override
    public List<QuestionDTO> getAllByTestId(String testId) throws ServiceException {
        List<QuestionDTO> questionDTOS = new ArrayList<>();
        long id = getTestId(testId);

        try {
            List<Question> questions = questionDAO.getAllByTestId(id);
            questions.forEach(question -> questionDTOS.add(convertQuestionToDTO(question)));
        } catch (DAOException e) {
            throw new ServiceException(e);
        }

        return questionDTOS;
    }

    /**
     * Updates Question's text, testId. Validates question's text.
     * Converts QuestionDTO to Question
     * @param entity DTO to be updated
     * @throws ServiceException - may wrap DAOException or be thrown as IncorrectFormatException
     */
    @Override
    public void update(QuestionDTO entity) throws ServiceException {
        validateQuestionText(entity.getText());

        try {
            questionDAO.update(convertDTOToQuestion(entity));
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Deletes Question entity from database. Validates id.
     * @param idString id as a String to validate and convert to long
     * @throws ServiceException - may wrap DAOException or be thrown as NoSuchQuestionException
     */
    @Override
    public void delete(String idString) throws ServiceException {
        long questionId = getQuestionId(idString);

        try {
            questionDAO.delete(questionId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
