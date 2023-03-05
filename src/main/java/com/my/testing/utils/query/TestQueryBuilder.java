package com.my.testing.utils.query;

import java.util.*;
import static com.my.testing.controller.actions.constants.Parameters.*;

/**
 * TestQueryBuilder. Able to build query to obtain sorted, ordered and limited list of tests
 *
 * @author Khelemendyk Dmytro
 * @version 1.0
 */
public class TestQueryBuilder extends QueryBuilder {
    private static final String TEST_DOT_ID = "test.id";
    /** Contains set of allowed sort fields */
    private static final Set<String> TEST_SORT_FIELDS_SET = new HashSet<>();

    static {
        TEST_SORT_FIELDS_SET.add(NAME);
        TEST_SORT_FIELDS_SET.add(DIFFICULTY_ID);
        TEST_SORT_FIELDS_SET.add(NUMBER_OF_QUERIES);
        TEST_SORT_FIELDS_SET.add(DURATION);
    }

    /**
     * Sets id as default sort field
     */
    public  TestQueryBuilder() {
        super(TEST_DOT_ID);
    }

    /**
     * @return concrete group by
     */
    @Override
    protected String getGroupByQuery() {
        return " GROUP BY " + TEST_DOT_ID + " ";
    }

    /**
     * @param sortField field that should be checked
     * @return sort field if it's suitable or id field by default
     */
    @Override
    protected String checkSortField(String sortField) {
        if (TEST_SORT_FIELDS_SET.contains(sortField.toLowerCase())) {
            return sortField;
        }

        return TEST_DOT_ID;
    }
}
