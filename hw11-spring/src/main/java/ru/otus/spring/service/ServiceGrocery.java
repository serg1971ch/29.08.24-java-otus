package ru.otus.spring.service;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.repository.ProductRepository;

import java.util.Scanner;

@Service
@NoArgsConstructor(force = true)
public class ServiceGrocery {
    private final ServiceCart serviceCart;
    Scanner scanner = new Scanner(System.in);

    public ServiceGrocery(ServiceCart serviceCart) {
        this.serviceCart = serviceCart;
    }

    public void startShopping() {
        while (true) {
            System.out.println("\nДоступные команды:");
            System.out.println("1. Добавить товар в корзину");
            System.out.println("2. Удалить товар из корзины");
            System.out.println("3. Показать корзину");
            System.out.println("4. Выход");

            System.out.print("Введите номер команды: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Введите id товара для добавления: ");
                    int addId = scanner.nextInt();
                    serviceCart.addProduct(addId);
                    break;
                case 2:
                    int removeId = scanner.nextInt();
                    serviceCart.removeProduct(removeId);
                    break;
                case 3:
                    serviceCart.showCart();
                    break;
                case 4:
                    break;
            }
        }
    }
}