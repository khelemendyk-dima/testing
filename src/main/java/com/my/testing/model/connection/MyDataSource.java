package com.my.testing.model.connection;

import com.zaxxer.hikari.*;

import javax.sql.DataSource;
import java.util.Properties;

import static com.my.testing.model.connection.ConnectionConstants.*;

public class MyDataSource {
    private static DataSource dataSource;

    public static synchronized DataSource getDataSource(Properties properties) {
        if (dataSource == null) {
            HikariConfig config = getHikariConfig(properties);
            dataSource = new HikariDataSource(config);
        }
        return dataSource;
    }

    private static HikariConfig getHikariConfig(Properties properties) {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(properties.getProperty(URL_PROPERTY));
        config.setUsername(properties.getProperty(DB_USER));
        config.setPassword(properties.getProperty(DB_PASSWORD));
        config.setDriverClassName(properties.getProperty(DRIVER));
        config.addDataSourceProperty(CACHE_PREPARED_STATEMENT, properties.getProperty(CACHE_PREPARED_STATEMENT));
        config.addDataSourceProperty(CACHE_SIZE, properties.getProperty(CACHE_SIZE));
        config.addDataSourceProperty(CACHE_SQL_LIMIT, properties.getProperty(CACHE_SQL_LIMIT));
        return config;
    }

    private MyDataSource() {}
}

