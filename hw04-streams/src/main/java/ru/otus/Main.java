package ru.otus;

import java.util.HashMap;

import static ru.otus.TaskState.*;


public class Main {
    public static void main(String[] args) {
        HashMap<Long, Task> mainTasks = new HashMap<>();
        TaskDao taskDao = new TaskDao();
        TaskService taskService = new TaskServiceImpl(taskDao);

        Task task1 = new Task(1, "Подьем в 6.30", CLOSED);
        Task task2 = new Task(2, "Сварить кашу и позавтракать", CLOSED);
        Task task3 = new Task(3, "Сделать зарядку", CLOSED);
        Task task4 = new Task(4, "Домашнее задание по теме Stream", AT_WORK);
        Task task5 = new Task(5, "Собраться на любимую работу", OPEN);
        Task task6 = new Task(6, "Купить и сварить куриную грудку кошке Машке", OPEN);
        Task task7 = new Task(7, "Выучить 5 английских слов", OPEN);
        Task task8 = new Task(8, "Подготовить справки для предоставлния в налоговую", AT_WORK);
        Task task9 = new Task(9, "Инструктаж и задания на день от жены", AT_WORK);

        taskDao.save(task1);
        taskDao.save(task2);
        taskDao.save(task3);
        taskDao.save(task4);
        taskDao.save(task5);
        taskDao.save(task6);
        taskDao.save(task7);
        taskDao.save(task8);
        taskDao.save(task9);

        System.out.println(taskService.getTasksByStatus(2) + "\n");
        System.out.println("Проверка наличия задачи с указанным ID = " + taskDao.getTasks().get(task3.getId()).getId() + " =====> " + taskService.taskExistsById(3) + "\n");
        System.out.println("Получение списка задач в отсортированном виде по статусу задачи " + taskService.getSortedTasks() + "\n");
        System.out.println("Kоличествo задач по статусу " + taskDao.getTasks().get(task5.getId()).getStatus() + " =========> " + taskService.countTasksByStatus(0));
    }
}