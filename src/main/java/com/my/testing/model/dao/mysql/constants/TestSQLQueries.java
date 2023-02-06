package com.my.testing.model.dao.mysql.constants;

public final class TestSQLQueries {
    public static final String UTIL_GET_TEST = "SELECT test.id AS id, name, subject_id, difficulty_id, duration, " +
            "COUNT(DISTINCT question.id) as number_of_queries FROM test LEFT JOIN question ON test.id=question.test_id ";
    public static final String GROUP_BY = "GROUP BY test.id ";
    public static final String GET_TEST_BY_ID = UTIL_GET_TEST + "WHERE test.id=? " + GROUP_BY;
    public static final String GET_TESTS = UTIL_GET_TEST + GROUP_BY;
    public static final String GET_SORTED = UTIL_GET_TEST + " %s";
    public static final String GET_NUMBER_OF_RECORDS = "SELECT COUNT(test.id) AS number_of_records FROM test %s";
    public static final String ADD_TEST = "INSERT INTO test (name, subject_id, difficulty_id, duration) VALUES (?, ?, ?, ?)";
    public static final String UPDATE_TEST = "UPDATE test SET name=?, subject_id=?, difficulty_id=?, duration=? WHERE id=?";
    public static final String DELETE_TEST = "DELETE FROM test WHERE id=?";

    private TestSQLQueries() {}
}
