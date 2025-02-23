package ru.otus.hw12_hiber.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Entity(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product", unique = true, nullable = false)
    private String nameProduct;

    @ManyToMany(mappedBy = "products", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Person> persons;

    @Column(name="price")
    private int price;

    public Product(String nameProduct, List<Person> persons,int price) {
        this.nameProduct = nameProduct;
        this.persons = persons;
        this.price = price;
    }

    public Product() {

    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    public void setPerson(List<Person> persons) {
        this.persons = persons;
    }

    public List<Person> getPerson() {
        return persons;
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }

    public List<Person> getPersons() {
        return persons;
    }

    @Override
    public String toString() {
        return "Product{id=" + id + ", name='" + nameProduct + "', price=" + price + "}";
    }
}
