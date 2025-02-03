package ru.otus;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import lombok.extern.java.Log;
import ru.otus.repository.AbstractRepository;
import ru.otus.service.ProductService;


@Log
public class Main {
    public static void main(String[] args) throws IOException, SQLException {
        String fileName = "hw08-jdbc/src/main/resources/table.txt";
        AnnotationProcessor processor = new AnnotationProcessor();

        processor.createTableProduct(fileName);
//        processor.executeSQL();

        log.info("Processing file " + fileName + "is migrated into DB like table product");

        AbstractRepository<Product> repository = new AbstractRepository<>(DataSource.getInstance());
        log.info("DataSource " + DataSource.getInstance() + "is created");

        ProductService service  = new ProductService(repository);
        service.createProduct(new Product(1,"Bread", 47));
        service.createProduct(new Product(2, "Milk", 65));
        service.createProduct(new Product(3, "Potato", 52));
        service.createProduct(new Product(4,"Sausage", 152));
        service.createProduct(new Product(5,"Fish", 230));

        List<Product> products =  service.findByAll();
        System.out.println(products);

    }
}