import service.InMemoryTaskManager;
import service.Status;
import model.Epic;
import model.SubTask;
import model.Task;
import service.TaskManager;
import util.Managers;

public class Main {

    public static void main(String[] args) {
        TaskManager manager = Managers.getDefault();
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
        printAllTasks(manager);
        //
        System.out.println("%%%%%%%%%%%%%%%%\nМеняем статусы и проверяем историю.\n%%%%%%%%%%%%%%%%");
        //Обновляем task1
        Task task3 = new Task(task1.getName(),task1.getDescription(), task1.getId(), Status.IN_PROGRESS);
        manager.updateTask(task3);
        //Обновляем subTask1
        SubTask subTask4 = new SubTask(subTask1.getName(), subTask1.getDescription(), subTask1.getId(), Status.DONE, subTask1.getEpicId());
        //Обновляем subTask3
        SubTask subTask5 = new SubTask(subTask3.getName(), subTask3.getDescription(), subTask3.getId(), Status.IN_PROGRESS, subTask3.getEpicId());
        manager.updateSubTask(subTask4);
        manager.updateSubTask(subTask5);
        for(int i=1; i<=5;i++){
            manager.getEpicById(i);
            manager.getSubTaskById(i);
            manager.getTaskById(i);
        }
        //
        printAllTasks(manager);
        //
        System.out.println("%%%%%%%%%%%%%%%%\nУдаляем task & epic.\n%%%%%%%%%%%%%%%%");
        manager.deleteTaskById(2);
        manager.deleteEpicById(3);
        //
        printAllTasks(manager);
        //
        System.out.println("%%%%%%%%%%%%%%%%\nУдаляем subTask\n%%%%%%%%%%%%%%%%");
        manager.deleteSubTaskById(7);
        //
        printAllTasks(manager);
        //
    }
    private static void printAllTasks(TaskManager manager) {
        System.out.println("Задачи:");
        for (Task task : manager.getAllTasks()) {
            System.out.println(task);
        }
        System.out.println("Эпики:");
        for (Epic epic : manager.getAllEpics()) {
            System.out.println(epic);

            for (SubTask subTask : epic.getAllSubTask()) {
                System.out.println("--> " + subTask);
            }
        }
        System.out.println("Подзадачи:");
        for (Task subtask : manager.getAllSubTasks()) {
            System.out.println(subtask);
        }

        System.out.println("История:");
        for (Task task : manager.getHistory()) {
            System.out.println(task);
        }
    }
}
