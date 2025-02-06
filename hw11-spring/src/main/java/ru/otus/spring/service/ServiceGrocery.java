package ru.otus.spring.service;

import lombok.NoArgsConstructor;
import ru.otus.spring.repository.ProductRepository;

import java.util.Scanner;

@NoArgsConstructor(force = true)
public class ServiceGrocery {
    private  ServiceCart serviceCart;
    private final ProductRepository productRepository;
    Scanner scanner = new Scanner(System.in);

    public ServiceGrocery(ProductRepository productRepository) {
        this.serviceCart = new ServiceCartImpl();
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
            int command = Integer.parseInt(scanner.nextLine().trim());

            switch (command) {
                case 1:
                    System.out.println("Доступные товары:");
                    productRepository.getAllProducts();
                    break;
                case 2:
                    System.out.print("Введите id товара для добавления: ");
                    int addId = scanner.nextInt();
                    serviceCart.addProduct(addId);
                    break;
                case 3:
                    int removeId = scanner.nextInt();
                    serviceCart.removeProduct(removeId);
                    break;
                case 4:
                    serviceCart.showCart();
                    break;
                case 5:
                    break;
            }
        }
    }
}