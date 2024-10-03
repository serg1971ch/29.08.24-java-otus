package ru.otus;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicLong;

public class TaskDao {
    private HashMap<Long, Task> tasks;
    AtomicLong id = new AtomicLong(1);

    public TaskDao() {
        this.tasks = new HashMap<>();
    }

    public HashMap<Long, Task> getTasks() {
        return tasks;
    }

    public void setTasks(HashMap<Long, Task> tasks) {
        this.tasks = tasks;
    }

    public void save(Task task) {
        if (task == null && task.getId() == 0) {
            task.setId(id.incrementAndGet());
        }
        tasks.put(task.getId(), task);
    }
}
