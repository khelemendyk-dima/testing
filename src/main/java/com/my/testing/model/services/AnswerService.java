package com.my.testing.model.services;

import com.my.testing.dto.AnswerDTO;
import com.my.testing.exceptions.ServiceException;

import java.util.List;

public interface AnswerService extends Service<AnswerDTO> {
    void add(AnswerDTO answerDTO) throws ServiceException;
    List<AnswerDTO> getAllByQuestionId(String questionId) throws ServiceException;

    void deleteAllByQuestionId(String questionId) throws ServiceException;
}
