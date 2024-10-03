package ru.otus;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class TaskServiceImpl implements TaskService{

    private final TaskDao taskDao;

    public TaskServiceImpl(TaskDao taskDao) {
        this.taskDao = taskDao;
    }

    // 1. Получение списка задач по выбранному статусу
    @Override
    public List<Task> getTasksByStatus(int status) {
        return this.taskDao.getTasks().values().stream()
                .filter(task -> task.getState()==status)
                .collect(Collectors.toList());
    }

    // 2. Проверка наличия задачи с указанным ID
    @Override
    public boolean taskExistsById(int id) {
        return taskDao.getTasks().values().stream()
                .anyMatch(task -> task.getId() == id);
    }

    // 3. Получение списка задач в отсортированном по статусу виде
    @Override
    public List<Task> getSortedTasks() {
        return taskDao.getTasks().values().stream()
                .sorted(Comparator.comparing(Task::getState))
                .collect(Collectors.toList());
    }

    // 4. Подсчет количества задач по определенному статусу
    @Override
    public int countTasksByStatus(int id) {
        return (int) taskDao.getTasks().values().stream()
                .filter(task -> task.getState() == id)
                .count();
    }

    // Метод для вывода задач на консоль
    public void printTasks(List<Task> tasks) {
        tasks.forEach(System.out::println);
    }
}
