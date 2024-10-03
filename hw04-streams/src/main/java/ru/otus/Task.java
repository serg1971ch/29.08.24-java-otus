package ru.otus;

public class Task {
    private long id;
    private String name;
    private TaskState state;

    public Task(long id, String name, TaskState state) {
        this.id = id;
        this.name = name;
        this.state = state;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getState() {
        if(state == TaskState.OPEN){
            return 0;
        }
        if (state == TaskState.CLOSED){
            return 1;
        }
        return 2;
    }

    public void setState(TaskState state) {
        this.state = state;
    }

    public TaskState getStatus() {
        return state;
    }

    @Override
    public String toString() {
        return "\n" + " Task: " +
                "id = " + id +
                ", title = " + name +
                ", status = " + state;
    }
}
