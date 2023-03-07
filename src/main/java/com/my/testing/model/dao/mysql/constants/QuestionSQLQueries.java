package com.my.testing.model.dao.mysql.constants;

import lombok.*;

/**
 * Class that contains all SQL queries for QuestionDAO
 *
 * @author Khelemendyk Dmytro
 * @version 1.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class QuestionSQLQueries {
    public static final String GET_QUESTION_BY_ID = "SELECT * FROM question WHERE id=?";
    public static final String GET_QUESTIONS = "SELECT * FROM question";
    public static final String GET_QUESTIONS_BY_TEST_ID = "SELECT * FROM question WHERE test_id=?";
    public static final String ADD_QUESTION = "INSERT INTO question (text, test_id) VALUES (?, ?)";
    public static final String UPDATE_QUESTION = "UPDATE question SET text=?, test_id=? WHERE id=?";
    public static final String DELETE_QUESTION = "DELETE FROM question WHERE id=?";
}
