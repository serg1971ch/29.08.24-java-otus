package ru.otus;

import java.util.LinkedList;
import java.util.List;

public class MyThreadPool {
    private final int capacity;
    private final List<Worker> workers;
    private final LinkedList<Runnable> taskQueue;
    private boolean isShutdown = false;

    public MyThreadPool(int capacity) {
        this.capacity = capacity;
        this.workers = new LinkedList<>();
        this.taskQueue = new LinkedList<>();

        // Инициализация и запуск рабочих потоков
        for (int i = 0; i < capacity; i++) {
            Worker worker = new Worker();
            workers.add(worker);
            worker.start();
        }
    }

    // Метод для добавления новой задачи в пул
    public synchronized void execute(Runnable r) {
        if (isShutdown) {
            throw new IllegalStateException("ThreadPool has been shut down and cannot accept new tasks.");
        }
        taskQueue.addLast(r);
        notify(); // Уведомляем один из потоков, что появилась новая задача
    }

    // Метод для завершения работы пула
    public synchronized void shutdown() {
        isShutdown = true;
        notifyAll(); // Уведомляем все потоки, чтобы они могли завершить работу
    }

    // Класс Worker, представляющий рабочий поток
    private class Worker extends Thread {
        public void run() {
            while (true) {
                Runnable task;
                synchronized (MyThreadPool.this) {
                    while (taskQueue.isEmpty()) {
                        if (isShutdown) return; // Если отключен пул, завершаем поток
                        try {
                            MyThreadPool.this.wait(); // Ожидаем новую задачу
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    task = taskQueue.removeFirst(); // Получаем следующую задачу
                }
                try {
                    task.run(); // Выполняем задачу
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

