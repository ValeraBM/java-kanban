package model;

import service.Status;

import java.util.Objects;

public class Task {
    static private int counter = 1;
    private final String name;
    private final String description;
    private final int id;
    private Status status;


    public TaskType getType() {
        return TaskType.TASK;
    }

    private int getNewId(){
        return counter++;
    }

    /**
     *  Для создания новой задачи
     * @param name имя
     * @param description описание
     */
    public Task(String name, String description) {
        this.name = name;
        this.description = description;
        this.id = getNewId();
        this.status = Status.NEW;
    }

    /**
     *  Для обновления задачи
     * @param name имя
     * @param description описание
     */
    public Task(String name, String description, int id, Status status) {
        this.name = name;
        this.description = description;
        this.id = id;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }


    public int getId() {
        return id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Task{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", id=" + id +
                ", status=" + status +
                '}';
    }
}
