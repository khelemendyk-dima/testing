package com.my.testing.model.dao.mysql.constants;

import lombok.*;

/**
 * Class that contains all SQL queries for AnswerDAO
 *
 * @author Khelemendyk Dmytro
 * @version 1.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AnswerSQLQueries {
    public static final String GET_ANSWER_BY_ID = "SELECT * FROM answer WHERE id=?";
    public static final String GET_ANSWERS = "SELECT * FROM answer";
    public static final String GET_ANSWERS_BY_QUESTION_ID = "SELECT * FROM answer WHERE question_id=?";
    public static final String ADD_ANSWER = "INSERT INTO answer (text, is_correct, question_id) VALUES (?, ?, ?)";
    public static final String UPDATE_ANSWER = "UPDATE answer SET text=?, is_correct=?, question_id=? WHERE id=?";
    public static final String DELETE_ANSWER = "DELETE FROM answer WHERE id=?";
    public static final String DELETE_ALL_BY_QUESTION_ID = "DELETE FROM answer WHERE question_id=?";
}
