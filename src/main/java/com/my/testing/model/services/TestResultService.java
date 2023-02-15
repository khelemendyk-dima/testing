package com.my.testing.model.services;

import com.my.testing.dto.TestResultDTO;
import com.my.testing.exceptions.ServiceException;

import java.util.List;

public interface TestResultService extends Service<TestResultDTO> {
    void add(TestResultDTO testResultDTO) throws ServiceException;

    List<TestResultDTO> getAllByUserId(String idString) throws ServiceException;
}
