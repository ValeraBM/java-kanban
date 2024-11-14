package service;

import model.Epic;
import model.SubTask;
import model.Task;
import model.TaskType;
import service.history.HistoryManager;
import util.Managers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryTaskManager implements TaskManager {


    private final Map<Integer, Task> tasks = new HashMap<>();
    private final Map<Integer, Epic> epics = new HashMap<>();
    private final Map<Integer, SubTask> subtasks = new HashMap<>();
    private final HistoryManager historyManager = Managers.getDefaultHistory();

    /**
     * Получить список всех задач
     *
     * @return список всех задач
     */
    @Override
    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public List<Epic> getAllEpics() {
        return new ArrayList<>(epics.values());
    }

    @Override
    public List<SubTask> getAllSubTasks() {
        return new ArrayList<>(subtasks.values());
    }

    /**
     * Очистка списка.
     */
    @Override
    public void deleteAllTasks() {
        tasks.clear();
    }

    @Override
    public void deleteAllEpics() {
        epics.clear();
        subtasks.clear();
    }

    /**
     * Удаляет сабтаски из общей мапы.
     * Чистит мапы в эпиках. Ставит стутс NEW.
     */
    @Override
    public void deleteAllSubTasks() {
        subtasks.clear();
        for (Epic epic : epics.values()) {
            epic.deleteAllSubTask();
        }

    }

    /**
     * Получение по идентификатору.
     */
    @Override
    public Task getTaskById(int id) {
        Task task = tasks.get(id);
        addToHistory(task);
        return task;
    }

    @Override
    public Epic getEpicById(int id) {
        Epic epic = epics.get(id);
        addToHistory(epic);
        return epic;
    }

    @Override
    public SubTask getSubTaskById(int id) {
        SubTask subTask = subtasks.get(id);
        addToHistory(subTask);
        return subTask;
    }

    /**
     * Добавление задачи
     */
    @Override
    public void addTask(Task task) {
        if (task != null && task.getType() == TaskType.TASK)
            tasks.put(task.getId(), task);
    }

    @Override
    public void addEpic(Epic epic) {
        if (epic != null && epic.getType() == TaskType.EPIC)
            epics.put(epic.getId(), epic);
    }

    @Override
    public void addSubTask(SubTask subTask) {
        if (subTask != null && subTask.getType() == TaskType.SUBTASK) {
            Epic epic = epics.get(subTask.getEpicId());
            if (epic != null) {
                subtasks.put(subTask.getId(), subTask);
                epic.addSubTask(subTask);
            }
        }
    }


    /**
     * Обновление задачи
     */
    @Override
    public void updateTask(Task task) {
        tasks.put(task.getId(), task);
    }

    @Override
    public void updateEpic(Epic epic) {
        epics.put(epic.getId(), epic);
    }

    @Override
    public void updateSubTask(SubTask subTask) {
        addSubTask(subTask);
    }


    /**
     * Удаление по идентификатору
     */
    @Override
    public void deleteTaskById(int id) {
        tasks.remove(id);
    }

    @Override
    public void deleteEpicById(int id) {
        Epic epic = epics.get(id);
        if (epic != null) {
            epics.remove(id);
            for (SubTask subTask : epic.getAllSubTask()) {
                subtasks.remove(subTask.getId());
            }
        }
    }

    @Override
    public void deleteSubTaskById(int id) {
        SubTask subTask = subtasks.get(id);
        if (subTask != null) {
            Epic epic = epics.get(subTask.getEpicId());
            if (epic != null) {
                epic.deleteSubTask(id);
            }
            subtasks.remove(id);
        }
    }

    /**
     * Получение истории
     */
    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }

    /**
     * Добавление в историю
     */
    private void addToHistory(Task task) {
        if (task != null)
            historyManager.add(task);
    }
}
