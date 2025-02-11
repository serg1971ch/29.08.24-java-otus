package ru.otus.spring.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.model.Product;
import ru.otus.spring.repository.ProductRepository;
import ru.otus.spring.service.ServiceCart;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


@Service
public class ServiceCartImpl implements ServiceCart {
    private ProductRepository productRepository;
    private List<Product> items;
    Logger logger = Logger.getLogger(this.getClass().getName());

    public ServiceCartImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
        items = new ArrayList<>();
    }

    public ServiceCartImpl() {

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
