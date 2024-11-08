package ru.otus;

import java.util.List;

public interface TaskService {

    List<Task> getTasksByStatus(int status);

    boolean taskExistsById(int id);

    List<Task> getSortedTasks();

    int countTasksByStatus(int status);
}
