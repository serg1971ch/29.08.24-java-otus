 package ru.otus;

public class Main {
    public static void main(String[] args) {
        MyThreadPool threadPool = new MyThreadPool(3); // Создаем пул с 3 рабочими потоками

        // Добавляем задачи в пул
        for (int i = 0; i < 10; i++) {
            final int taskId = i;
            threadPool.execute(() -> {
                System.out.println("Task " + taskId + " is running.");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        // Завершаем пул
        threadPool.shutdown();
    }
}