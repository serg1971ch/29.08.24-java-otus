package ru.otus;

import lombok.Getter;
import lombok.extern.java.Log;

import java.sql.*;

@Log
public class DataSource {
    private static DataSource instance;
    @Getter
    private Connection connection;
    private Statement statement;

    private final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private final String USER = "postgres";
    private final String PASSWORD = "1234";

    DataSource() {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            statement = connection.createStatement();
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
}
