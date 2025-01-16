package ru.otus;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.otus.javaprodi.application.Application;
import ru.otus.javaprodi.application.services.GameProcessor;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(Application.class);
        GameProcessor gameProcessor = context.getBean(GameProcessor.class);

        gameProcessor.startGame();
    }
}