package ru.otus;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.spring.service.ServiceGrocery;

public class Main {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");
        ServiceGrocery serviceGrocery = context.getBean(ServiceGrocery.class);

        serviceGrocery.startShopping();
    }
}