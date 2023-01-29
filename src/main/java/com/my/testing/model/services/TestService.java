package com.my.testing.model.services;

import com.my.testing.dto.TestDTO;
import com.my.testing.exceptions.ServiceException;

import java.util.List;

public interface TestService extends Service<TestDTO> {
    void add(TestDTO testDTO) throws ServiceException;

    List<TestDTO> getSorted(String query) throws ServiceException;

    int getNumberOfRecords(String filter) throws ServiceException;
}
