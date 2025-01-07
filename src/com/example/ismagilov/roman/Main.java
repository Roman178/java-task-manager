package com.example.ismagilov.roman;


public class Main {

    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();

        taskManager.addTask("clean the room");
        taskManager.addTask("buy groceries");
        taskManager.addTask("jogging");
        taskManager.addTask("call to bank");

        taskManager.removeTask(3);
        taskManager.removeTask(50);

        taskManager.editTask(1, "get cash");
        taskManager.editTask(40, "get gas");

        taskManager.markTaskAsCompleted(1);
        taskManager.markTaskAsCompleted(60);

        taskManager.showTasks();
    }
}