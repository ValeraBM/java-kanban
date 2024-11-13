package service;

import model.Epic;
import model.SubTask;
import model.Task;

import java.util.List;

public interface TaskManager {
    /**
     * Получить список всех задач
     */
    List<Task> getAllTasks();

    List<Epic> getAllEpics();

    List<SubTask> getAllSubTasks();

    /**
     * Удаление задач.
     */
    void deleteAllTasks();

    void deleteAllEpics();

    void deleteAllSubTasks();

    /**
     * Получение по идентификатору.
     */
    Task getTaskById(int id);

    Epic getEpicById(int id);

    SubTask getSubTaskById(int id);

    /**
     * Добавление задачи
     */
    void addTask(Task task);

    void addEpic(Epic epic);

    void addSubTask(SubTask subTask);

    /**
     * Обновление задачи
     */
    void updateTask(Task task);

    void updateEpic(Epic epic);

    void updateSubTask(SubTask subTask);

    /**
     * Удаление по идентификатору
     */
    void deleteTaskById(int id);

    void deleteEpicById(int id);

    void deleteSubTaskById(int id);

    /**
     * Получение истории
     */
    List<Task> getHistory();
}
