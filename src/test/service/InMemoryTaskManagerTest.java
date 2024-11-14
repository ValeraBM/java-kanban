package service;

import model.Epic;
import model.SubTask;
import model.Task;
import org.junit.jupiter.api.Test;
import util.Managers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class InMemoryTaskManagerTest {

    @Test
    void getAllTasksTest() {
        TaskManager manager = Managers.getDefault();
        manager.addTask(new Task("Test getAllTasks1", "Test description"));
        manager.addTask(new Task("Test getAllTasks2", "Test description"));
        manager.addTask(new Task("Test getAllTasks3", "Test description"));
        List<Task> taskList = manager.getAllTasks();
        assertEquals(3, taskList.size(), "Непр. размер списка тасков");
        assertEquals("Test getAllTasks2", taskList.get(1).getName(), "Ошибка в списке задач");
    }

    @Test
    void getAllEpicsTest() {
        TaskManager manager = Managers.getDefault();
        manager.addEpic(new Epic("Test getAllEpics1", "Test description"));
        manager.addEpic(new Epic("Test getAllEpics2", "Test description"));
        Epic epic = new Epic("Test getAllEpics3", "Test description");
        manager.addEpic(epic);
        List<Epic> epicList = manager.getAllEpics();
        assertEquals(3, epicList.size(), "Непр. размер списка эпиков");
        assertEquals("Test getAllEpics3", manager.getEpicById(epic.getId()).getName(), "Ошибка в списке эпиков");
    }

    @Test
    void getAllSubTusksTest() {
        TaskManager manager = Managers.getDefault();
        Epic epic = new Epic("Test getAllEpics1", "Test addNewEpic description");
        manager.addEpic(epic);
        manager.addSubTask(new SubTask("Test getAllSubTusks1", "Test description", epic.getId()));
        manager.addSubTask(new SubTask("Test getAllSubTusks2", "Test description", epic.getId()));
        //Неправильный номер эпика - не должно добавляться
        manager.addSubTask(new SubTask("Test getAllSubTusks3", "Test description", epic.getId() + 1));
        List<SubTask> staskList = manager.getAllSubTasks();
        assertEquals(2, staskList.size(), "Непр. размер списка тасков");
        assertEquals("Test getAllSubTusks2", staskList.get(1).getName(), "Ошибка в списке задач");
    }

    @Test
    void deleteAllTasksTest() {
        TaskManager manager = Managers.getDefault();
        manager.addTask(new Task("Test getAllTasks1", "Test description"));
        Task task = new Task("Test getAllTasks2", "Test description");
        manager.addTask(task);
        manager.addTask(new Task("Test getAllTasks3", "Test description"));
        List<Task> taskList = manager.getAllTasks();
        assertEquals(3, taskList.size(), "Непр. размер списка тасков");
        assertEquals("Test getAllTasks2", manager.getTaskById(task.getId()).getName(), "Ошибка в списке задач");
        manager.deleteAllTasks();
        taskList = manager.getAllTasks();
        assertEquals(0, taskList.size(), "Непр. размер списка тасков после удаления");
    }

    @Test
    void deleteAllEpicsTest() {
        TaskManager manager = Managers.getDefault();
        manager.addEpic(new Epic("Test getAllEpics1", "Test description"));
        Epic epic = new Epic("Test getAllEpics2", "Test description");
        manager.addEpic(epic);
        manager.addEpic(new Epic("Test getAllEpics3", "Test description"));
        List<Epic> epicList = manager.getAllEpics();
        assertEquals(3, epicList.size(), "Непр. размер списка эпиков");
        assertEquals("Test getAllEpics2", manager.getEpicById(epic.getId()).getName(), "Ошибка в списке эпиков");
        manager.deleteAllEpics();
        epicList = manager.getAllEpics();
        assertEquals(0, epicList.size(), "Непр. размер списка эпиков после удаления");
    }

    @Test
    void deleteAllSubTasksTest() {
        TaskManager manager = Managers.getDefault();
        Epic epic = new Epic("Test getAllEpics1", "Test addNewEpic description");
        manager.addEpic(epic);
        final int epicId = epic.getId();
        manager.addSubTask(new SubTask("Test getAllSubTusks1", "Test description", epicId));
        manager.addSubTask(new SubTask("Test getAllSubTusks2", "Test description", epicId));
        //Неправильный номер эпика - не должно добавляться
        manager.addSubTask(new SubTask("Test getAllSubTusks3", "Test description", epicId + 1));
        List<SubTask> staskList = manager.getAllSubTasks();
        assertEquals(2, staskList.size(), "Непр. размер списка тасков");
        manager.deleteAllSubTasks();
        staskList = manager.getAllSubTasks();
        assertEquals(0, staskList.size(), "Непр. размер списка тасков - д.б. 0");
        //Проверка статуса эпика
        assertEquals(Status.NEW, manager.getEpicById(epicId).getStatus(), "Непр. статус эпика после удаления всех подзадач");
    }

    @Test
    void getTaskByIdTest() {
        TaskManager manager = Managers.getDefault();
        Task task = new Task("Test getAllTasks1", "Test description");
        manager.addTask(task);
        assertEquals(task, manager.getTaskById(task.getId()), "Ошибка в getTaskByIdTest");
    }

    @Test
    void getEpicByIdTest() {
        TaskManager manager = Managers.getDefault();
        Epic epic = new Epic("Test getAllEpics1", "Test description");
        manager.addEpic(epic);
        assertEquals(epic, manager.getEpicById(epic.getId()), "Ошибка в getEpicByIdTest");
    }

    @Test
    void getSubTaskByIdTest() {
        TaskManager manager = Managers.getDefault();
        Epic epic = new Epic("Test getAllEpics1", "Test description");
        manager.addEpic(epic);
        SubTask subTask = new SubTask("Test getSubTaskByIdTest", "Test description", epic.getId());
        manager.addSubTask(subTask);
        assertEquals(subTask, manager.getSubTaskById(subTask.getId()), "Ошибка в getSubTaskByIdTest");
    }

    @Test
    void addTaskTest() {
        TaskManager manager = Managers.getDefault();
        Task task = new Task("Test addNewTask", "Test addNewTask description");
        final int taskId = task.getId();
        manager.addTask(task);

        final Task savedTask = manager.getTaskById(taskId);

        assertNotNull(savedTask, "Задача не найдена.");
        assertEquals(task, savedTask, "Задачи не совпадают.");

        final List<Task> tasks = manager.getAllTasks();

        assertNotNull(tasks, "Задачи не возвращаются.");
        assertEquals(1, tasks.size(), "Неверное количество задач.");
        assertEquals(task, tasks.get(0), "Задачи не совпадают.");
    }

    @Test
    void addEpicTest() {
        TaskManager manager = Managers.getDefault();
        Epic epic = new Epic("Test addEpicTest", "Test addEpicTest description");
        final int epicId = epic.getId();
        manager.addEpic(epic);

        final Epic savedEpic = manager.getEpicById(epicId);

        assertNotNull(savedEpic, "Эпик не найден.");
        assertEquals(epic, savedEpic, "Эпики не совпадают.");

        final List<Epic> epics = manager.getAllEpics();

        assertNotNull(epics, "Эпики не возвращаются.");
        assertEquals(1, epics.size(), "Неверное количество Эпиков.");
        assertEquals(epic, epics.get(0), "Эпики не совпадают.");
    }

    @Test
    void addSubTaskTest() {
        TaskManager manager = Managers.getDefault();
        Epic epic = new Epic("Test addEpicTest", "Test addEpicTest description");
        manager.addEpic(epic);
        SubTask subTask = new SubTask("Test addSubTaskTest", "Test addSubTaskTest description", epic.getId());
        final int subTaskId = subTask.getId();
        manager.addSubTask(subTask);

        final SubTask savedSubTask = manager.getSubTaskById(subTaskId);

        assertNotNull(savedSubTask, "Подзадача не найдена.");
        assertEquals(subTask, savedSubTask, "Подзадачи не совпадают.");

        final List<SubTask> subTasks = manager.getAllSubTasks();

        assertNotNull(subTasks, "Подзадачи не возвращаются.");
        assertEquals(1, subTasks.size(), "Неверное количество подзадач.");
        assertEquals(subTask, subTasks.get(0), "Подзадачи не совпадают.");
    }

    @Test
    void updateTaskTest() {
        TaskManager manager = Managers.getDefault();
        String descr = "Test updateTaskTest description";
        String descrUpd = descr + " - Update";

        Task task = new Task("Test updateTaskTest", descr);
        final int taskId = task.getId();
        Task taskUpd = new Task("Test updateTaskTest - Update", descrUpd, taskId, Status.DONE);
        manager.addTask(task);
        assertEquals(task, manager.getTaskById(taskId), "Задачи не совпадают");
        assertEquals(descr, manager.getTaskById(taskId).getDescription(), "Описание не совпадают");
        manager.updateTask(taskUpd);
        assertEquals(taskUpd, manager.getTaskById(taskId), "Задачи не совпадают");
        assertEquals(descrUpd, manager.getTaskById(taskId).getDescription(), "Описание не совпадают");
    }

    @Test
    void updateEpicTest() {
        TaskManager manager = Managers.getDefault();
        Epic epic = new Epic("Test addEpicTest", "Test addEpicTest description");
        final int epicId = epic.getId();
        manager.addEpic(epic);

        final Task savedTask = manager.getEpicById(epicId);

        assertNotNull(savedTask, "Эпик не найден.");
        assertEquals(epic, savedTask, "Эпики не совпадают.");

        final List<Epic> epics = manager.getAllEpics();

        assertNotNull(epics, "Эпики не возвращаются.");
        assertEquals(1, epics.size(), "Неверное количество Эпиков.");
        assertEquals(epic, epics.get(0), "Эпики не совпадают.");
    }

    @Test
    void updateSubTaskTest() {
        TaskManager manager = Managers.getDefault();
        String descr = "Test updateSubTaskTest description";
        String descrUpd = descr + " - Update";

        Epic epic = new Epic("Test addEpicTest", "Test addEpicTest description");
        final int epicId = epic.getId();
        manager.addEpic(epic);

        SubTask subTask = new SubTask("Test updateSubTaskTest", descr, epicId);
        final int taskId = subTask.getId();
        SubTask subTaskUpd = new SubTask("Test updateSubTaskTest - Update", descrUpd, taskId, Status.DONE, epicId);

        manager.addSubTask(subTask);
        assertEquals(subTask, manager.getSubTaskById(taskId), "Задачи не совпадают");
        assertEquals(descr, manager.getSubTaskById(taskId).getDescription(), "Описание не совпадают");

        manager.updateSubTask(subTaskUpd);
        assertEquals(subTaskUpd, manager.getSubTaskById(taskId), "Задачи не совпадают");
        assertEquals(descrUpd, manager.getSubTaskById(taskId).getDescription(), "Описание не совпадают");
        //Проверка статуса эпика
        epic = manager.getEpicById(epicId);
        assertEquals(Status.DONE, epic.getStatus(), "Непр. статус эпика после обновления сабтаски");

    }

    @Test
    void deleteTaskByIdTest() {
        TaskManager manager = Managers.getDefault();
        Task task = new Task("Test addNewTask", "Test addNewTask description");
        final int taskId = task.getId();
        manager.addTask(task);
        manager.deleteTaskById(taskId);
        assertNull(manager.getTaskById(taskId), "Ошибка удаления задачи");
    }

    @Test
    void deleteEpicByIdTest() {
        TaskManager manager = Managers.getDefault();
        Epic epic = new Epic("Test addEpicTest", "Test addEpicTest description");
        final int epicId = epic.getId();
        manager.addEpic(epic);
        manager.deleteEpicById(epicId);
        assertNull(manager.getEpicById(epicId), "Ошибка удаления эпика");

    }

    @Test
    void deleteSubTaskByIdTest() {
        TaskManager manager = Managers.getDefault();
        Epic epic = new Epic("Test addEpicTest", "Test addEpicTest description");
        manager.addEpic(epic);
        SubTask subTask = new SubTask("Test addSubTaskTest", "Test addSubTaskTest description", epic.getId());
        final int subTaskId = subTask.getId();
        manager.addSubTask(subTask);
        manager.deleteSubTaskById(subTaskId);
        assertNull(manager.getSubTaskById(subTaskId), "Ошибка удаления подзадачи");
    }

    @Test
    void getHistoryTest() {
        TaskManager manager = Managers.getDefault();
        Task task = new Task("Test addNewTask", "Test addNewTask description");
        final int taskId = task.getId();
        manager.addTask(task);
        manager.getTaskById(taskId);
        Epic epic = new Epic("Test addEpicTest", "Test addEpicTest description");
        manager.addEpic(epic);
        SubTask subTask = new SubTask("Test addSubTaskTest", "Test addSubTaskTest description", epic.getId());
        final int subTaskId = subTask.getId();
        manager.addSubTask(subTask);
        manager.getEpicById(epic.getId());
        manager.getSubTaskById(subTaskId);
        final List<Task> history = manager.getHistory();
        assertEquals(3, history.size(), "История - неправильный размер");
    }

    @Test
    void addToHistoryTest() {
        TaskManager manager = Managers.getDefault();
        Task task = new Task("Test addNewTask", "Test addNewTask description");
        final int taskId = task.getId();
        manager.addTask(task);
        manager.getTaskById(taskId);

        final List<Task> history = manager.getHistory();
        assertNotNull(history, "История пустая.");
        assertEquals(1, history.size(), "История пустая.");
        Task savedTask = history.get(0);
        assertEquals(task, savedTask, "Задача в истории не совпадает с исходной");
        assertEquals("Test addNewTask", savedTask.getName(), "Имя задачи в истории не совпадает с исходным");
    }

    @Test
    void insertIntoTaskMapWrongType() {
        TaskManager manager = Managers.getDefault();
        Task task = new Task("Test addNewTask", "Test addNewTask description");
        manager.addTask(task);
        Epic epic = new Epic("Test addEpicTest", "Test addEpicTest description");
        //Пытаемся запихнуть в Task
        manager.addTask(epic);
        SubTask subTask = new SubTask("Test addSubTaskTest", "Test addSubTaskTest description", epic.getId());
        //Пытаемся запихнуть в Task
        manager.addTask(subTask);
        List<Task> tasks = manager.getAllTasks();
        assertEquals(1, tasks.size(), "Неправильная типизация");

    }
}