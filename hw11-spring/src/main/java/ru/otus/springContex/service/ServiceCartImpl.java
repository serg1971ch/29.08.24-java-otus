package ru.otus.springContex.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.springContex.model.Product;
import ru.otus.springContex.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Service
public class ServiceCartImpl implements ServiceCart {
    private final ProductRepository productRepository;
    private List<Product> items;
    Logger logger = Logger.getLogger(this.getClass().getName());

    @Autowired
    public ServiceCartImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
        items = new ArrayList<>();
    }

    public void addProduct(Integer productId) {
        Product product = productRepository.getProductById(productId);
        items.add(product);
        logger.info("Adding product " + product.getName());
    }


    public void removeProduct(Integer id) {
        Product removedProduct = productRepository.getProductById(id);
        if (removedProduct != null) {
            items.remove(removedProduct);
            System.out.println(removedProduct.getName() + " удален из корзины.");
        } else {
            System.out.println("Товар с id " + id + " не найден в корзине.");
        }
    }

    public void showCart() {
        if (items.isEmpty()) {
            System.out.println("Корзина пуста.");
        } else {
            System.out.println("Товары в корзине:");
            items.forEach(System.out::println);
        }
    }

    @Override
    public List<Product> getProducts() {
        List<Product> products = productRepository.getProducts();
        return products.stream().toList();
    }
}
