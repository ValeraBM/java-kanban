package util;

import service.InMemoryTaskManager;
import service.TaskManager;
import service.history.HistoryManager;
import service.history.InMemoryHistoryManager;

public class Managers {
    public static TaskManager getDefault(){
        return new InMemoryTaskManager();
    }
    public static HistoryManager getDefaultHistory(){
        return new InMemoryHistoryManager();
    }
}
