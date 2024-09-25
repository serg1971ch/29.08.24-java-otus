package ru.otus;


import ru.otus.annotations.BeforeSuite;
import ru.otus.annotations.AfterSuite;
import ru.otus.annotations.Test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TestsRunner {

    public static void runTests(Class<?> testClass) {
        Method beforeSuiteMethod = null;
        Method afterSuiteMethod = null;
        List<Method> testMethods = new ArrayList<>();

        for (Method method : testClass.getDeclaredMethods()) {
            if (method.isAnnotationPresent(BeforeSuite.class)) {
                if (beforeSuiteMethod == null) {
                    beforeSuiteMethod = method;
                } else {
                    System.out.println("В классе может быть только один метод с аннотацией @BeforeSuite");
                }
            }
            if (method.isAnnotationPresent(AfterSuite.class)) {
                if (afterSuiteMethod == null) {
                    afterSuiteMethod = method;
                } else {
                    System.out.println("В классе может быть только один метод с аннотацией @AfterSuite");
                }
            }
            if (method.isAnnotationPresent(Test.class)) {
                testMethods.add(method);
            }
        }

        // Выполняем метод BeforeSuite
        try {
            if (beforeSuiteMethod != null) {
                beforeSuiteMethod.invoke(testClass.getDeclaredConstructor().newInstance());
            }

            // Сортируем тестовые методы по приоритету
            testMethods.sort(Comparator.comparingInt(m -> m.getAnnotation(Test.class).priority()));
            int successCount = 0;
            int failureCount = 0;

            // Выполняем тестовые методы
            for (Method testMethod : testMethods) {
                try {
                    testMethod.invoke(testClass.getDeclaredConstructor().newInstance());
                    successCount++;
                } catch (Exception e) {
                    System.out.println("Тест " + testMethod.getName() + " упал: " + e.getCause());
                    failureCount++;
                }
            }

            // Выполняем метод AfterSuite
            if (afterSuiteMethod != null) {
                afterSuiteMethod.invoke(testClass.getDeclaredConstructor().newInstance());
            }

            // Выводим статистику
            System.out.println("Статистика тестирования:");
            System.out.println("Всего тестов: " + testMethods.size());
            System.out.println("Успешно пройдено: " + successCount);
            System.out.println("Упало: " + failureCount);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
