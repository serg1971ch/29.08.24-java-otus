
package ru.otus.springContex.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.springContex.model.Product;
import ru.otus.springContex.repository.ProductRepository;

import java.util.List;
import java.util.Scanner;

@Service
public class ServiceGrocery {
    private final ServiceCart serviceCart;
    Scanner scanner = new Scanner(System.in);
    private final ProductRepository productRepository;

    @Autowired
    public ServiceGrocery(ProductRepository productRepository) {
        this.productRepository = productRepository;
        this.serviceCart = new ServiceCartImpl(productRepository);
    }

    public void startShopping() {
        while (true) {
            System.out.println("\nДоступные команды:");
            System.out.println("1. Показать все товары");
            System.out.println("2. Добавить товар в корзину");
            System.out.println("3. Удалить товар из корзины");
            System.out.println("4. Показать корзину");
            System.out.println("5. Выход");

            String commandInput = scanner.nextLine().trim();

            if (commandInput.equals("1")) {
                checkCommand("1");
                System.out.println("Доступные товары:");
                System.out.println(productRepository.getProducts());
                continue;
            }
            if (commandInput.equals("2")) {
                System.out.print("Введите id товара для добавления: ");
                int addId = scanner.nextInt();
                serviceCart.addProduct(addId);
            }

            if (commandInput.equals("3")) {
                System.out.print("Введите id товара для удалерния: ");
                int removeId = scanner.nextInt();
                serviceCart.removeProduct(removeId);
                continue;
            }
            if (commandInput.equals("4")) {
                checkCommand("4");
                System.out.print("Показать корзину: ");
                serviceCart.showCart();
                continue;
            }
            if (commandInput.equals("5")) {
                System.out.print("Для выхода нажмите 5: ");
                checkCommand("5");
                break;
            }
        }
    }

    private void checkCommand(String command) {
        if (command.isEmpty()) {
            System.out.println("Команда не может быть пустой. Пожалуйста, попробуйте снова.");
        }
        try {
            command = String.valueOf(Integer.parseInt(command));
        } catch (NumberFormatException e) {
            System.out.println("Некорректный ввод. Пожалуйста, введите число от 1 до 5.");
        }
    }
}
