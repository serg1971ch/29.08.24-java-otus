package ru.otus.spring.service;

import org.springframework.stereotype.Service;

//Создаем бин Cart, в который можно добавлять и удалять товары по id из репозитория.
@Service
public interface ServiceCart {
    void addProduct(Integer productId);
    void removeProduct(Integer id);
    void showCart();
}
