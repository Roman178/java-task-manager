package com.example.ismagilov.roman;

import java.util.Arrays;
import java.util.Scanner;

public class TaskManagerCLI {
    private final Scanner scanner = new Scanner(System.in);
    private final TaskManager taskManager = new TaskManager();

    static {
        TaskManager taskManagerExample = new TaskManager();
        String[] arr = {"Пример задачи 1", "Пример задачи 2","Пример задачи 3","Пример задачи 4" };
        for (String taskDescription: arr) {
            taskManagerExample.addTask(taskDescription);
        }
        taskManagerExample.showTasks();
    }

    public void runInteractively() {
        while (true) {
            System.out.println("""
                
                
                Введите команду для управления задачами. Параметры через запятую с пробелом.
                Команды:
                add, сходить в магазин[opt.], 0[opt.], Дом[opt.], LOW[opt.]
                edit, ID-3, сходить на рынок[opt.], 1[opt.], Дом[opt.], LOW[opt.]
                remove, ID-3
                show tasks
                show completed tasks
                show pending tasks
                show tasks by priority, HIGH
                search tasks, bla-bla
                exit
                """);
            String command = scanner.nextLine();
            handleCommand(command);

            if (command.equals("exit")) break;
        }

    }

    private void handleCommand(String command) {
        String[] arr = command.split(", ");
        CLICommands mainCommand = CLICommands.fromString(arr[0]);
        String[] parameters = Arrays.copyOfRange(arr, 1, arr.length);

        if (mainCommand == null) {
            System.out.println("Невалидная команда");
            return;
        }

        switch (mainCommand) {
            case ADD:
                taskManager.addTask(parameters);
                break;
            case EDIT:
                taskManager.handleEditingTask(parameters);
                break;
            case REMOVE:
                taskManager.removeTask(parameters);
                break;
            case SHOW_TASKS:
                taskManager.showTasks();
                break;
            case SHOW_COMPLETED_TASKS:
                taskManager.showCompletedTasks();
                break;
            case SHOW_PENDING_TASKS:
                taskManager.showPendingTasks();
                break;
            case SEARCH_TASKS:
                taskManager.searchTasks(parameters);
                break;
            case SHOW_TASKS_BY_PRIORITY:
                taskManager.showTasksByPriority(parameters);
            case EXIT:
                System.out.println("exited");
                break;
            default:
                break;

        }
    }
}
