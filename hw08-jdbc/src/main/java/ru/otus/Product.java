package ru.otus;

import ru.otus.annotations.*;

@RepositoryTable(title = "products")
@MyTable(name = "products")
public class Product {
    @RepositoryIdField
    private int id;
    @RepositoryField
    @MyField(name = "titles")
    private String title;
    @RepositoryField
    @MyField(name = "prices")
    private double price;

    public Product(int id, String title, double price) {
        this.id = id;
        this.title = title;
        this.price = price;
    }

    // Геттеры и сеттеры
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Product: " +
                "id = " + id +
                ", title='" + title + '\'' +
                ", price=" + price;
    }
}
