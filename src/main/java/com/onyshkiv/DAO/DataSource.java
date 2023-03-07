package com.onyshkiv.DAO;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import java.util.ResourceBundle;

public class DataSource {
    private static HikariConfig config = new HikariConfig();
    private static HikariDataSource ds;

    static {
        ResourceBundle resourceBundle = ApplicationResourceBundle.resourceBundle;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        config.setJdbcUrl(resourceBundle.getString("jdbcUrl"));
        config.setUsername(resourceBundle.getString("dataSource.username"));
        config.setPassword(resourceBundle.getString("dataSource.password"));
        config.setConnectionTimeout(Long.parseLong(resourceBundle.getString("dataSource.connection-timeout")));
        config.setMinimumIdle(Integer.parseInt(resourceBundle.getString("dataSource.minimum-idle")));
        config.setMaximumPoolSize(Integer.parseInt(resourceBundle.getString("dataSource.maximum-poll-size")));
        config.setMaxLifetime(Long.parseLong(resourceBundle.getString("dataSource.max-lifetime")));
        config.setDriverClassName(resourceBundle.getString("dataSource.driver"));
      //  config.setAutoCommit(Boolean.parseBoolean(resourceBundle.getString("dataSource.isAutoCommit")));
        ds = new HikariDataSource(config);
    }

    private DataSource() {
    }

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

}
