package com.my.testing.utils;

import com.my.testing.utils.query.QueryBuilder;
import com.my.testing.utils.query.TestQueryBuilder;

public class QueryBuilderUtil {
    public static QueryBuilder testQueryBuilder() {
        return new TestQueryBuilder();
    }

    private QueryBuilderUtil() {}
}
