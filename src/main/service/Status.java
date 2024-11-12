package service;

public enum Status {
    NEW("Task is created"), IN_PROGRESS("The task is being worked on"), DONE("The task is completed");
    private final String descr;
    Status(String descr){
        this.descr = descr;
    }
}
