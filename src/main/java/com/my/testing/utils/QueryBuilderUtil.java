package com.my.testing.utils;

import com.my.testing.utils.query.*;
import lombok.*;

/**
 * Factory to return concrete query builders
 *
 * @author Khelemendyk Dmytro
 * @version 1.0
 */

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class QueryBuilderUtil {
    /**
     * @return TestQueryBuilder to create query for get sorted list of tests
     */
    public static QueryBuilder testQueryBuilder() {
        return new TestQueryBuilder();
    }
}
