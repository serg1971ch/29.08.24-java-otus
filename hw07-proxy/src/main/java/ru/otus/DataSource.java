package ru.otus;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DataSource {
    private static DataSource instance;
    private Connection connection;

    private DataSource() {
        try {
            // Замените URL, USER, и PASSWORD на реальные данные вашей базы данных
            String URL = "jdbc:postgresql://localhost:5432/postgres";
            String USER = "postgres";
            String PASSWORD = "1234";
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static DataSource getInstance() {
        if (instance == null) {
            instance = new DataSource();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}
