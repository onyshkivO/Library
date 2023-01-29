package com.onyshkiv.DAO;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DataSource {
    private static HikariConfig config = new HikariConfig();
    private static HikariDataSource ds;

    static {
        try (InputStream input = new FileInputStream("src/main/resources/datasource.properties")) {
            Properties prop = new Properties();
            prop.load(input);
            config.setJdbcUrl(prop.getProperty("jdbcUrl"));
            config.setUsername(prop.getProperty("dataSource.username"));
            config.setPassword(prop.getProperty("dataSource.password"));
            config.setConnectionTimeout(Long.parseLong(prop.getProperty("dataSource.connection-timeout")));
            config.setMinimumIdle(Integer.parseInt(prop.getProperty("dataSource.minimum-idle")));
            config.setMaximumPoolSize(Integer.parseInt(prop.getProperty("dataSource.maximum-poll-size")));
            config.setMaxLifetime(Long.parseLong(prop.getProperty("dataSource.max-lifetime")));
            config.setAutoCommit(Boolean.parseBoolean(prop.getProperty("dataSource.isAutoCommit")));
            ds = new HikariDataSource(config);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private DataSource() {
    }

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

}
