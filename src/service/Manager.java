package service;

import model.Epic;
import model.SubTask;
import model.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Manager {


    private final Map<Integer, Task> tasks = new HashMap<>();
    private final Map<Integer, Epic> epics = new HashMap<>();
    private final Map<Integer, SubTask> subtasks = new HashMap<>();

    /**
     * Получить список всех задач
     *
     * @return список всех задач
     */
    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks.values()) ;
    }

    public List<Epic> getAllEpics() {
        return new ArrayList<>(epics.values()) ;
    }

    public List<SubTask> getAllSubTusks() {
        return new ArrayList<>(subtasks.values()) ;
    }

    /**
     * Очистка списка.
     */
    public void deleteAllTasks() {
        tasks.clear();
    }

    public void deleteAllEpics() {
        epics.clear();
        subtasks.clear();
    }

    /**
     * Удаляет сабтаски из общей мапы.
     * Чистит мапы в эпиках. Ставит стутс NEW.
     */
    public void deleteAllSubTasks() {
        subtasks.clear();
        for(Epic epic: epics.values()){
            epic.deleteAllSubTask();
        }

    }

    /**
     * Получение по идентификатору.
     */
    public Task getTaskById(int id) {
        return tasks.get(id);
    }

    public Epic getEpicById(int id) {
        return epics.get(id);
    }

    public SubTask getSubTaskById(int id) {
        return subtasks.get(id);
    }

    /**
     * Добавление задачи
     */
    public void addTask(Task task) {
        tasks.put(task.getId(), task);
    }

    public void addEpic(Epic epic) {
        epics.put(epic.getId(), epic);
    }

    public void addSubTask(SubTask subTask) {
        Epic epic = epics.get(subTask.getEpicId());
        if (epic != null) {
            subtasks.put(subTask.getId(), subTask);
            epic.addSubTask(subTask);
        }
    }


    /**
     * Обновление задачи
     */
    public void updateTask(Task task) {
        tasks.put(task.getId(), task);
    }

    public void updateEpic(Epic epic) {
        epics.put(epic.getId(), epic);
    }

    public void updateSubTask(SubTask subTask) {
        addSubTask(subTask);
    }


    /**
     * Удаление по идентификатору
     */
    public void deleteTaskById(int id) {
        tasks.remove(id);
    }

    public void deleteEpicById(int id) {
        Epic epic = epics.get(id);
        if (epic != null) {
            epics.remove(id);
            for (SubTask subTask : epic.getAllSubTask()) {
                subtasks.remove(subTask.getId());
            }
        }
    }

    public void deleteSubTaskById(int id) {
        SubTask subTask = subtasks.get(id);
        if (subTask != null) {
            Epic epic = epics.get(subTask.getEpicId());
            if (epic != null) {
                epic.deleteSubTask(id);
                epic.calculateStatus();
            }
            subtasks.remove(id);
        }
    }


}
