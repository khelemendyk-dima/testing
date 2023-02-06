package com.my.testing.model.services.implementation;

import com.my.testing.dto.QuestionDTO;
import com.my.testing.exceptions.*;
import com.my.testing.model.dao.QuestionDAO;
import com.my.testing.model.entities.Question;
import com.my.testing.model.services.QuestionService;

import java.util.*;

import static com.my.testing.utils.ConvertorUtil.*;
import static com.my.testing.utils.ValidatorUtil.*;

public class QuestionServiceImpl implements QuestionService {
    private final QuestionDAO questionDAO;

    public QuestionServiceImpl(QuestionDAO questionDAO) {
        this.questionDAO = questionDAO;
    }

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

    @Override
    public void update(QuestionDTO entity) throws ServiceException {
        validateQuestionText(entity.getText());

        try {
            questionDAO.update(convertDTOToQuestion(entity));
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

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
