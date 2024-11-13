package model;

import service.Status;

public class SubTask extends Task{
    //Для каждой подзадачи известно, в рамках какого эпика она выполняется.
    private int epicId;
    public SubTask(String name, String description,int epicId) {
        super(name, description);
        setEpicId(epicId);
    }

    public SubTask(String name, String description, int id, Status status, int epicId) {
        super(name, description, id, status);
        this.epicId = epicId;
    }

    @Override
    public TaskType getType() {
        return TaskType.SUBTASK;
    }

    public void setEpicId(int epicId){
        this.epicId = epicId;
    }

    public int getEpicId() {
        return epicId;
    }

    @Override
    public String toString() {
        return "SubTask{" +
                "name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", id=" + getId() +
                ", status=" + getStatus() +
                ", epicId=" + getEpicId() +
                "}\n";
    }
}
