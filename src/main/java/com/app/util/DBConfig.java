package com.app.util;

public class DBConfig {

    private DBConfig() {}

    public static final String HOST = "localhost";
    public static final String PORT = "3306";
    public static final String USER = "root";
    public static final String PASSWORD = "";
    public static final String DB_NAME = "school_db";

    public static String URL = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DB_NAME ;
}
