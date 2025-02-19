package ru.otus;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import ru.otus.springContex.service.ServiceGrocery;

@ComponentScan("ru.otus.springContex")
public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(Main.class);
        ServiceGrocery serviceGrocery = context.getBean(ServiceGrocery.class);
        serviceGrocery.startShopping();
    }
}