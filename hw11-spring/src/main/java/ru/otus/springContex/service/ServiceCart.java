package ru.otus.springContex.service;

import ru.otus.springContex.model.Product;

import java.util.List;

//Создаем бин Cart, в который можно добавлять и удалять товары по id из репозитория.

public interface ServiceCart {
    void addProduct(Integer productId);
    void removeProduct(Integer id);
    void showCart();
    List<Product> getProducts();
}
