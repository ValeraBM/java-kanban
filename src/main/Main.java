import service.InMemoryTaskManager;
import service.Status;
import model.Epic;
import model.SubTask;
import model.Task;

public class Main {

    public static void main(String[] args) {
        InMemoryTaskManager manager = new InMemoryTaskManager();
        System.out.println("Поехали!");
        //task
        Task task1 = new Task("Task1","test task1");
        Task task2 = new Task("Task2","test task2");
        manager.addTask(task1);
        manager.addTask(task2);
        //epic
        Epic epic1 = new Epic("Epic1", "test epic1");
        Epic epic2 = new Epic("Epic2", "test epic2");
        manager.addEpic(epic1);
        manager.addEpic(epic2);
        //subTask
        SubTask subTask1 = new SubTask("SubTask1", "test SubTask1", epic1.getId());
        SubTask subTask2 = new SubTask("SubTask2", "test SubTask2", epic1.getId());
        manager.addSubTask(subTask1);
        manager.addSubTask(subTask2);
        SubTask subTask3 = new SubTask("SubTask3", "test SubTask3", epic2.getId());
        manager.addSubTask(subTask3);
        //
        System.out.println(manager.getAllTasks());
        System.out.println(manager.getAllEpics());
        System.out.println(manager.getAllSubTusks());
        //
        System.out.println("%%%%%%%%%%%%%%%%\nМеняем статусы.\n%%%%%%%%%%%%%%%%");
        //Обновляем task1
        Task task3 = new Task(task1.getName(),task1.getDescription(), task1.getId(), Status.IN_PROGRESS);
        manager.updateTask(task3);
        //Обновляем subTask1
        SubTask subTask4 = new SubTask(subTask1.getName(), subTask1.getDescription(), subTask1.getId(), Status.DONE, subTask1.getEpicId());
        //Обновляем subTask3
        SubTask subTask5 = new SubTask(subTask3.getName(), subTask3.getDescription(), subTask3.getId(), Status.IN_PROGRESS, subTask3.getEpicId());
        manager.updateSubTask(subTask4);
        manager.updateSubTask(subTask5);
        //
        System.out.println(manager.getAllTasks());
        System.out.println(manager.getAllEpics());
        System.out.println(manager.getAllSubTusks());
        //
        System.out.println("%%%%%%%%%%%%%%%%\nУдаляем task & epic.\n%%%%%%%%%%%%%%%%");
        manager.deleteTaskById(2);
        manager.deleteEpicById(3);
        //
        System.out.println(manager.getAllTasks());
        System.out.println(manager.getAllEpics());
        System.out.println(manager.getAllSubTusks());
        //
        System.out.println("%%%%%%%%%%%%%%%%\nУдаляем subTask\n%%%%%%%%%%%%%%%%");
        manager.deleteSubTaskById(7);
        //
        System.out.println(manager.getAllTasks());
        System.out.println(manager.getAllEpics());
        System.out.println(manager.getAllSubTusks());
        //
    }
}
