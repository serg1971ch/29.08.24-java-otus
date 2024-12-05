package ru.otus;

import lombok.extern.java.Log;
import ru.otus.annotations.MyField;
import ru.otus.annotations.MyTable;
import ru.otus.exeptions.NotFoundExeptions;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Log
public class AnnotationProcessor {

    private final Connection connection;

    public AnnotationProcessor() {
        this.connection = DataSource.getInstance().getConnection();
    }

    public void createTableProduct(String filepath) {
        String sql = readSQLFile(filepath);
        try {
            if(connection == null) {
                connection.createStatement().execute(sql);
            }
        } catch (SQLException e) {
            throw new NotFoundExeptions("Error inserting product: " + e.getMessage());
        }
    }

    public String readSQLFile(String filepath) {
        StringBuilder sqlBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            String line;
            while ((line = br.readLine()) != null) {
                sqlBuilder.append(line).append("\n");
            }
        } catch (ExeptionJDBC | FileNotFoundException j) {
            j.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        log.info(sqlBuilder.toString());
        return sqlBuilder.toString();
    }

    public String createChangeNameColumnStatementAnnotation(Class<?> clazz) {
        StringBuilder sql = null;
        if (clazz.isAnnotationPresent(MyTable.class)) {
            MyTable myTable = clazz.getAnnotation(MyTable.class);
            sql = new StringBuilder("ALTER TABLE " + myTable.name() + " RENAME COLUMN ");
            log.info("ALTER TABLE " + myTable.name() + " RENAME COLUMN ");
            Field[] fields = clazz.getDeclaredFields();
            sql.append(fields[1].getName()).append(" TO ");
            if (fields[1].isAnnotationPresent(MyField.class)) {
                MyField myField1 = fields[1].getAnnotation(MyField.class);
                sql.append(myField1.name()).append(" ;");
                log.info("SQL " + sql);
            }
            sql.append("ALTER TABLE ").append(myTable.name()).append(" RENAME COLUMN ").append(fields[2].getName()).append(" TO ");
            if (fields[2].isAnnotationPresent(MyField.class)) {
                MyField myField2 = fields[2].getAnnotation(MyField.class);
                sql.append(myField2.name()).append(" ;");
                log.info("SQL " + sql);
            }
        }
        return sql.toString();
    }

    public void executeSQL() {
        String changeColumnQuery = createChangeNameColumnStatementAnnotation(Product.class);
        try (PreparedStatement stmt = connection.prepareStatement(changeColumnQuery)) {
            stmt.execute();
        } catch (ExeptionJDBC | SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }
}

