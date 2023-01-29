package com.my.testing.utils.query;

import org.apache.logging.log4j.*;

import java.util.HashSet;
import java.util.Set;

import static com.my.testing.controller.actions.constants.Parameters.*;

public class TestQueryBuilder extends QueryBuilder {
    private static final Logger logger = LogManager.getLogger(TestQueryBuilder.class);
    private static final String TEST_DOT_ID = "test.id";
    private static final Set<String> TEST_SORT_FIELDS_SET = new HashSet<>();

    static {
        TEST_SORT_FIELDS_SET.add(NAME);
        TEST_SORT_FIELDS_SET.add(DIFFICULTY_ID);
        TEST_SORT_FIELDS_SET.add(NUMBER_OF_QUERIES);
    }

    public  TestQueryBuilder() {
        super(TEST_DOT_ID);
    }

    @Override
    protected String getGroupByQuery() {
        return " GROUP BY " + TEST_DOT_ID + " ";
    }

    @Override
    protected String checkSortField(String sortField) {
        if (TEST_SORT_FIELDS_SET.contains(sortField.toLowerCase())) {
            return sortField;
        }
        logger.info("wrong sort field");
        return TEST_DOT_ID;
    }
}
