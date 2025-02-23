package ru.otus;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import ru.otus.springContex.service.ServiceGrocery;

@ComponentScan("ru.otus.springContex")
public class MainGrocery {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(MainGrocery.class);
        ServiceGrocery serviceGrocery = context.getBean(ServiceGrocery.class);
        serviceGrocery.startShopping();
    }
}