package ru.otus;

import ru.otus.annotations.*;


@MyTable(name = "products")
public class Product {

    private int id;

    @MyField(name = "titles")
    private String title;

    public Product(String title, double price) {
        this.title = title;
        this.price = price;
    }

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
