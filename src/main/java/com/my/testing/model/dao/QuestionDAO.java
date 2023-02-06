package com.my.testing.model.dao;

import com.my.testing.exceptions.DAOException;
import com.my.testing.model.entities.Question;

import java.util.List;

public interface QuestionDAO extends DAO<Question> {
    List<Question> getAllByTestId(long testId) throws DAOException;
}
