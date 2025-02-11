package ru.otus.spring.model;

import lombok.Data;
import ru.otus.spring.model.Product;

@Data
public class Cart {
    private int id;
    private Product product;
}
