package com.my.testing.model.connection;

import com.zaxxer.hikari.*;
import org.apache.logging.log4j.*;

import java.io.*;
import java.sql.*;
import java.util.Properties;

import static com.my.testing.model.connection.ConnectionConstants.*;

public class DataSource {
    private static final Logger logger = LogManager.getLogger(DataSource.class);
    private static final String CONNECTION_FILE = "connection.properties";
    private static final HikariConfig config = new HikariConfig();
    private static final HikariDataSource ds;

    static {
        Properties properties = getProperties();
        config.setJdbcUrl(properties.getProperty(URL_PROPERTY));
        config.setUsername(properties.getProperty(USER_NAME));
        config.setPassword(properties.getProperty(PASSWORD));
        config.setDriverClassName(properties.getProperty(DRIVER));
        config.addDataSourceProperty(CACHE_PREPARED_STATEMENT, properties.getProperty(CACHE_PREPARED_STATEMENT));
        config.addDataSourceProperty(CACHE_SIZE, properties.getProperty(CACHE_SIZE));
        config.addDataSourceProperty(CACHE_SQL_LIMIT, properties.getProperty(CACHE_SQL_LIMIT));
        ds = new HikariDataSource(config);
    }

    private DataSource() {}

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    private static Properties getProperties() {
        Properties properties = new Properties();
        try (InputStream resource = DataSource.class.getClassLoader().getResourceAsStream(CONNECTION_FILE)) {
            properties.load(resource);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return properties;
    }
}

