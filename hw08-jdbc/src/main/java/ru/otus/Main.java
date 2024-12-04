package ru.otus;

import java.io.IOException;
import java.sql.SQLException;

import lombok.extern.java.Log;
@Log
public class Main {
    public static void main(String[] args) throws IOException, SQLException {
        String fileName = "hw08-jdbc/src/main/resources/table.txt";
        AnnotationProcessor processor = new AnnotationProcessor();

        processor.createTableProduct(fileName);
        processor.executeSQL();

        log.info("Processing file " + fileName + "is migrated into DB like table product");
    }
}