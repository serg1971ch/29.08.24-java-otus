package ru.otus;

import ru.otus.annotations.AfterSuite;
import ru.otus.annotations.BeforeSuite;
import ru.otus.annotations.Test;

public class SampleTest {
    @BeforeSuite
    public void setUp() {
        System.out.println("Перед запуском тестов");
    }

    @Test(priority = 1)
    public void testA() {
        System.out.println("Тест A выполняется");
    }

    @Test(priority = 2)
    public void testB() {
        System.out.println("Тест B выполняется");
    }

    @Test(priority = 10)
    public void testC() {
        System.out.println("Тест C выполняется");
    }

    @AfterSuite
    public void tearDown() {
        System.out.println("После запуска тестов");
    }
}
