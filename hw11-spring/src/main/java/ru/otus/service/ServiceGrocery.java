package ru.otus.service;

import lombok.NoArgsConstructor;
import ru.otus.hw11_spring.repository.ProductRepository;

import java.util.Scanner;

@NoArgsConstructor(force = true)
public class ServiceGrocery {
    private final ServiceCart serviceCart;
    private final ProductRepository productRepository;
    Scanner scanner = new Scanner(System.in);

    public ServiceGrocery(ServiceCart serviceCart, ProductRepository productRepository) {
        this.serviceCart = serviceCart;
        this.productRepository = productRepository;
    }

    public void startShopping() {
        while (true) {
            System.out.println("\nДоступные команды:");
            System.out.println("1. Показать все товары");
            System.out.println("2. Добавить товар в корзину");
            System.out.println("3. Удалить товар из корзины");
            System.out.println("4. Показать корзину");
            System.out.println("5. Выход");

            System.out.print("Введите номер команды: ");
            int command = scanner.nextInt();

            switch (command) {
                case 1:
                    System.out.println("Доступные товары:");
                    productRepository.getAllProducts();
                    break;
                case 2:
                    System.out.print("Введите id товара для добавления: ");
                    int addId = scanner.nextInt();
                    serviceCart.addProduct(addId);
                case 3:
                    int removeId = scanner.nextInt();
                    serviceCart.removeProduct(removeId);
                case 4:
                    serviceCart.showCart();
                case 5:
                    break;
            }
        }
    }
}