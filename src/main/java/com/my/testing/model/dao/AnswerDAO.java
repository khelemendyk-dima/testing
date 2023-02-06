package com.my.testing.model.dao;

import com.my.testing.exceptions.DAOException;
import com.my.testing.model.entities.Answer;

import java.util.List;

public interface AnswerDAO extends DAO<Answer> {
    List<Answer> getAllByQuestionId(long questionId) throws DAOException;
    void deleteAllByQuestionId(long questionId) throws DAOException;
}
