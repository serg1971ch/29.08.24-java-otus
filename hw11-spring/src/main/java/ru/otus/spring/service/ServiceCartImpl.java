package ru.otus.spring.service;

import ru.otus.spring.model.Product;
import ru.otus.spring.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


//При каждом запросе корзины из контекста, должна создаваться новая корзина.
public class ServiceCartImpl implements ServiceCart {
    private final ProductRepository productRepository;
    private final List<Product> items;
    Logger logger = Logger.getLogger(this.getClass().getName());

    public ServiceCartImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
        items = new ArrayList<>();
    }

    public void addProduct(Integer productId) {
        Product product = productRepository.getProductById(productId);
        logger.info("Adding product " + product.getName());
        items.add(product);
        System.out.println(product.getName() + " добавлен в корзину.");
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
}
