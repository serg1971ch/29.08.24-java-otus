package ru.otus.service;

import ru.otus.Product;
import ru.otus.exeptions.NotFoundExeptions;
import ru.otus.repository.AbstractRepository;

import java.sql.SQLException;
import java.util.List;

public class ProductService {
    private final AbstractRepository<Product> productRepository;

    public ProductService(AbstractRepository<Product> productRepository) {
        this.productRepository = productRepository;
    }

    public void createProduct(Product product) throws SQLException {
        productRepository.create(product);
    }

    public void updateProduct(Product product) {
        productRepository.update(product);
    }

    public void deleteProduct(int productId) {
        productRepository.delete(productId);
    }

    public void findById(Product product) {
        productRepository.findById(product);
    }

    public List<Product> findByAll() {
        return  productRepository.getAllProducts();
    }
}
