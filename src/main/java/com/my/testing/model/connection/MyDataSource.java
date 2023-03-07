package com.my.testing.model.connection;

import com.zaxxer.hikari.*;
import lombok.*;
import org.apache.logging.log4j.*;

import javax.sql.DataSource;
import java.util.Properties;

import static com.my.testing.model.connection.ConnectionConstants.*;

/**
 * Class to configure and obtain HikariDataSource. Use it to connect to database
 *
 * @author Khelemendyk Dmytro
 * @version 1.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MyDataSource {
    private static final Logger logger = LogManager.getLogger(MyDataSource.class);
    private static DataSource dataSource;

    /**
     * Configures and obtains HikariDataSource
     * @param properties all required info to configure datasource
     * @return singleton instance of HikariDataSource
     */
    public static synchronized DataSource getDataSource(Properties properties) {
        if (dataSource == null) {
            HikariConfig config = getHikariConfig(properties);
            dataSource = new HikariDataSource(config);
            logger.info("Hikari pool instance was created");
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

        logger.info("Hikari pool was configured");
        return config;
    }
}

