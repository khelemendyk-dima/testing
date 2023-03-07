package com.my.testing.model.dao.mysql.constants;

import lombok.*;

/**
 * Class that contains all SQL queries for TestResultDAO
 *
 * @author Khelemendyk Dmytro
 * @version 1.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TestResultSQLQueries {

    /** Queries to use in another queries */
    public static final String UTIL_GET_TEST_RESULT = "SELECT test_result.id, user_id, test_id, result, " +
            "test.name AS test_name FROM test_result LEFT JOIN test ON test_id=test.id ";
    public static final String GROUP_BY = "GROUP BY test_result.id ";

    /** Queries to use in DAO methods */
    public static final String GET_TEST_RESULT_BY_ID = UTIL_GET_TEST_RESULT + "WHERE test_result.id=? " + GROUP_BY;
    public static final String GET_TEST_RESULTS = UTIL_GET_TEST_RESULT + GROUP_BY;
    public static final String GET_TEST_RESULTS_BY_USER_ID = UTIL_GET_TEST_RESULT + "WHERE user_id=? " + GROUP_BY +
            "ORDER BY test_result.id DESC";
    public static final String ADD_TEST_RESULT = "INSERT INTO test_result (user_id, test_id, result) VALUES (?,?,?)";
    public static final String UPDATE_TEST_RESULT = "UPDATE test_result SET user_id=?, test_id=?, result=? WHERE id=?";
    public static final String DELETE_TEST_RESULT = "DELETE FROM test WHERE id=?";
}
