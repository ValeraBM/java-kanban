package model;

import service.Status;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Epic extends Task {
    //Каждый эпик знает, какие подзадачи в него входят.
    // При обновлении эпика не забывать восстанавливать этот список
    private final Map<Integer, SubTask> subTaskMap = new HashMap<>();

    public Epic(String name, String description) {
        super(name, description);
    }

    @Override
    public TaskType getType() {
        return TaskType.EPIC;
    }

    public void addSubTask(SubTask subTask) {
        subTaskMap.put(subTask.getId(), subTask);
        calculateStatus();
    }

    public void deleteSubTask(int id) {
        subTaskMap.remove(id);
        calculateStatus();
    }

    public void deleteAllSubTask(){
        subTaskMap.clear();
        setStatus(Status.NEW);
    }


    public List<SubTask> getAllSubTask() {
        return new ArrayList<>(subTaskMap.values());
    }

    public void calculateStatus() {
        Status result;
        if (subTaskMap.isEmpty()) {
            setStatus(Status.NEW);
            return;
        }
        int newCnt = 0;
        int doneCnt = 0;
        for (SubTask st : subTaskMap.values()) {
            switch (st.getStatus()) {
                case NEW -> newCnt++;
                case DONE -> doneCnt++;
                case IN_PROGRESS -> {
                    setStatus(Status.IN_PROGRESS);
                    return;
                }
            }
        }
        if (newCnt > 0 && doneCnt == 0) result = Status.NEW;
        else if (newCnt == 0 && doneCnt > 0) result = Status.DONE;
        else result = Status.IN_PROGRESS;
        setStatus(result);
    }

    @Override
    public String toString() {
        return "Epic{" +
                "name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", id=" + getId() +
                ", status=" + getStatus() +
                '}';
    }
}
