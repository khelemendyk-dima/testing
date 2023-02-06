package com.my.testing.model.services;

import com.my.testing.dto.QuestionDTO;
import com.my.testing.exceptions.ServiceException;

import java.util.List;

public interface QuestionService extends Service<QuestionDTO> {
    void add(QuestionDTO questionDTO) throws ServiceException;
    List<QuestionDTO> getAllByTestId(String testId) throws ServiceException;

}
