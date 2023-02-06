package com.my.testing.model.services.implementation;

import com.my.testing.dto.AnswerDTO;
import com.my.testing.exceptions.*;
import com.my.testing.model.dao.AnswerDAO;
import com.my.testing.model.entities.Answer;
import com.my.testing.model.services.AnswerService;

import java.util.*;

import static com.my.testing.utils.ConvertorUtil.*;
import static com.my.testing.utils.ValidatorUtil.*;

public class AnswerServiceImpl implements AnswerService {
    private final AnswerDAO answerDAO;

    public AnswerServiceImpl(AnswerDAO answerDAO) {
        this.answerDAO = answerDAO;
    }

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

    @Override
    public void update(AnswerDTO entity) throws ServiceException {
        validateAnswerText(entity.getText());
        try {
            answerDAO.update(convertDTOToAnswer(entity));
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void delete(String idString) throws ServiceException {
        long answerId = getAnswerId(idString);

        try {
            answerDAO.delete(answerId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

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
