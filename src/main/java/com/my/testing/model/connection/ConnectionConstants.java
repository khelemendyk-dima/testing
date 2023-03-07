package com.my.testing.model.connection;

import lombok.*;

/**
 * Contains keys for properties to configure database connection
 *
 * @author Khelemendyk Dmytro
 * @version 1.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ConnectionConstants {
    public static final String URL_PROPERTY = "connection.url";
    public static final String DB_USER = "db.user";
    public static final String DB_PASSWORD = "db.password";
    public static final String DRIVER = "driver";
    public static final String CACHE_PREPARED_STATEMENT = "cachePrepStmts";
    public static final String CACHE_SIZE = "prepStmtCacheSize";
    public static final String CACHE_SQL_LIMIT = "prepStmtCacheSqlLimit";
}