package ru.otus;

import lombok.extern.java.Log;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.Connection;
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
            connection.createStatement().execute(sql);
        } catch (NotFoundExeptions | SQLException e) {
            e.printStackTrace();
        }

    }

    public String readSQLFile(String filepath)  {
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

    public static String createTableStatement(Class<?> clazz) {
        if (clazz.isAnnotationPresent(MyTable.class)) {
            MyTable myTable = clazz.getAnnotation(MyTable.class);
            StringBuilder sql = new StringBuilder("CREATE TABLE " + myTable.name() + " (");
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(MyField.class)) {
                    MyField myField = field.getAnnotation(MyField.class);
                    sql.append(myField.name()).append(" ").append(getSQLType(field)).append(", ");
                }
            }
            if (sql.length() > 0) {
                sql.setLength(sql.length() - 2); // Удаляем последнюю запятую
            }
            sql.append(");");
            return sql.toString();
        }
        return null;
    }

    private static String getSQLType(Field field) {
        String typeName = field.getType().getSimpleName();
        switch (typeName) {
            case "int": return "INTEGER";
            case "String": return "VARCHAR(255)";
            case "double": return "DOUBLE";
            default: return "VARCHAR(255)";
        }
    }
}
