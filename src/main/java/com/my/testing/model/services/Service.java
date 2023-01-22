package com.my.testing.model.services;

import com.my.testing.exceptions.ServiceException;
import java.util.List;

public interface Service<T> {
    T getById(String idString) throws ServiceException;

    List<T> getAll() throws ServiceException;

    void update(T entity) throws ServiceException;

    void delete(String idString) throws ServiceException;
}