package ru.otus.hw12_hiber.service;

import org.springframework.stereotype.Service;
import ru.otus.hw12_hiber.model.Person;
import ru.otus.hw12_hiber.model.Product;

import java.util.Scanner;
import java.util.*;

@Service
public class ScannerShopService {
    private final ShopService shopService;

    public ScannerShopService(ShopService shopService) {
        this.shopService = shopService;
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nShop Management Console:");
            System.out.println("1. Продукт, который нужно добавить в корзину покупателя");
            System.out.println("2. Обзор продуктов, который купил этот покупатель");
            System.out.println("3. Обзор покупателей, которые купили этот продукт");
            System.out.println("4. Удалить продукт");
            System.out.println("5. Удалить покупателя");
            System.out.println("6. Выход");

            System.out.print("Введите число из списка меню: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    addProductToClient(scanner);
                    break;
                case 2:
                    viewProductsByClient(scanner);
                    break;
                case 3:
                    viewClientsByProduct(scanner);
                    break;
                case 4:
                    deleteProduct(scanner);
                    break;
                case 5:
                    deleteClient(scanner);
                    break;
                case 6:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    private void addProductToClient(Scanner scanner) {
        System.out.print("Введите id продукта: ");
        Long productId = scanner.nextLong();
        System.out.print("Введите id покупателя: ");
        Long personId = scanner.nextLong();
        shopService.addProductToPerson(productId, personId);
    }

    private void viewProductsByClient(Scanner scanner) {
        System.out.print("Введите id покупателя: ");
        Long personId = scanner.nextLong();
        try {
            Set<Product> products = shopService.getProductsByPersonId(personId);
            System.out.println("Продукт куплен покупателем " + personId + ":");
            products.forEach(System.out::println);
        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void viewClientsByProduct(Scanner scanner) {
        System.out.print("Введите id продукта: ");
        Long productId = scanner.nextLong();
        List<Person> persons = shopService.getPersonsByProductId(productId);
        System.out.println("Покупатели, которые купили этот продукт " + productId + ":");
        persons.forEach(System.out::println);
    }

    private void deleteProduct(Scanner scanner) {
        System.out.print("Введите продукта ID, который нужно удалить: ");
        Long productId = scanner.nextLong();
        shopService.deleteProduct(productId);
        System.out.println("Продукт ID:  " + productId + " удален.");
    }

    private void deleteClient(Scanner scanner) {
        System.out.print("Введите id покупателя, который нужно удалить: ");
        Long personId = scanner.nextLong();
        shopService.deletePerson(personId);
        System.out.println("Покупатель с  ID " + personId + " удален.");
    }

}