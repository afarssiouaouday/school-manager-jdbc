package com.app.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private DBConnection() {}

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                DBConfig.URL,
                DBConfig.USER,
                DBConfig.PASSWORD
        );
    }
}
