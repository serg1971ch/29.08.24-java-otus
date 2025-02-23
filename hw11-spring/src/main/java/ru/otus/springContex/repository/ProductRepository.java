package ru.otus.springContex.repository;


import lombok.Getter;
import org.springframework.stereotype.Repository;
import ru.otus.springContex.model.Product;

import java.util.ArrayList;
import java.util.List;

@Getter
@Repository
public class ProductRepository {
    private final List<Product> products = new ArrayList<>();

    public ProductRepository() {
        initializeProducts();
    }

    private void initializeProducts() {
        products.add(new Product(1, "Bread", 42.99));
        products.add(new Product(2, "Milk", 65.49));
        products.add(new Product(3, "Meat", 415.75));
        products.add(new Product(4, "Potato", 55.99));
        products.add(new Product(5, "Onions", 22.30));
        products.add(new Product(6, "Chicken", 348.00));
        products.add(new Product(7, "Fish", 287.50));
        products.add(new Product(8, "Carrot", 22.99));
        products.add(new Product(9, "Cucumbers", 218.25));
        products.add(new Product(10, "Sausages", 149.99));
    }

    public Product getProductById(int id) {
        return products.stream()
                .filter(product -> product.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public List<Product> getProducts() {
        return products.stream().toList();
    }

    @Override
    public String toString() {
        return "ProductRepository{" +
                "products =" + getProducts() +
                '}';
    }
}
