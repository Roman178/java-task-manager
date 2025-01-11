package com.example.ismagilov.roman;

import java.util.*;

public class TaskManager {
    public static int maxTasks = 5;

    private List<Task> tasks = new ArrayList<>();
    private final Scanner scanner = new Scanner(System.in);

    public static Task createDefaultTask() {
        return new Task("Задача по умолчанию", false, "Общие");
    }

    static {
        TaskManager taskManager = new TaskManager();
        String[] arr = {"Пример задачи 1", "Пример задачи 2","Пример задачи 3","Пример задачи 4" };
        for (String taskDescription: arr) {
            taskManager.addTask(taskDescription);
        }
        taskManager.showTasks();
    }

    public void runInteractively() {
        System.out.println("""
                
                
                Введите команду для управления задачами. Параметры через запятую с пробелом.
                Команды:
                add, сходить в магазин[opt.], 0[opt.], Дом[opt.]
                edit, 3, сходить на рынок
                remove, 3
                show tasks
                show completed tasks
                show pending tasks
                search tasks, bla-bla
                exit
                """);
        String command = scanner.nextLine();
        handleCommand(command);

        if (!command.equals("exit")) {
            runInteractively();
        }
    }

    private void handleCommand(String command) {
        String[] arr = command.split(", ");
        String mainCommand = arr[0];
        String[] parameters = Arrays.copyOfRange(arr, 1, arr.length);

        switch (mainCommand) {
            case "add":
                handleAddingTask(parameters);
                break;
            case "edit":
                handleEditingTask(parameters);
                break;
            case "remove":
                removeTask(parameters);
                break;
            case "show tasks":
                showTasks();
                break;
            case "show completed tasks":
                showCompletedTasks();
                break;
            case "show pending tasks":
                showPendingTasks();
                break;
            case "search tasks":
                searchTasks(parameters[0]);
                break;
            case "exit":
                System.out.println("exited");
                break;
            default:
                System.out.println("Невалидная команда");
                break;

        }
    }

    private void handleAddingTask(String[] parameters) {
        switch (parameters.length) {
            case 0:
                addTask();
                break;
            case 1:
                addTask(parameters[0]);
                break;
            case 2: {
                BinaryValue isCompleted = BinaryValue.fromInt(Integer.parseInt(parameters[1]));
                addTask(parameters[0], isCompleted);
                break;
            }
            case 3: {
                BinaryValue isCompleted = BinaryValue.fromInt(Integer.parseInt(parameters[1]));
                addTask(parameters[0], isCompleted, parameters[2]);
                break;
            }
            default:
                break;
        }
    }

    private  void handleEditingTask(String[] parameters) {
        UUID id = UUID.fromString(parameters[0]);
        Optional<Task> task = tasks.stream().filter(t -> t.id.equals(id)).findFirst();

        task.ifPresentOrElse(tsk -> {
                    tsk.updateAll(parameters);
                    System.out.println("Задача с id " + id + " обновлена");
                },
                () -> System.out.println("Задача с id " + id + " не найдена"));
    }

    public void addTask() {
        addTask("Какая-то задача");
    }

    public void addTask(String task) {
        addTask(task, BinaryValue.ZERO);
    }

    public void addTask(String task, BinaryValue isCompleted) {
        addTask(task, isCompleted, "Общее");
    }

    public void addTask(String task, BinaryValue isCompletedNum, String category) {
        if (tasks.size() < maxTasks) {
            boolean isCompleted = isCompletedNum == BinaryValue.ONE;
            tasks.add(new Task(task, isCompleted, category));
            System.out.println("Задача: " + task + " добавлена");
        } else {
            System.out.println("Превышен лимит задач. Максимум: " + maxTasks);
        }
    }

    public void removeTask(String[] parameters) {
        UUID id = UUID.fromString(parameters[0]);
        List<Task> updatedTasks = tasks.stream().filter(t -> !t.id.equals(id)).toList();

        if (tasks.size() == updatedTasks.size()) {
            System.out.println("Задача с id " + id + " не найдена");
        } else {
            tasks = updatedTasks;
            System.out.println("Задача с id " + id + " удалена");
        }
    }

    public void showTasks() {
        if (tasks.isEmpty()) {
            System.out.println("Список задач пуст");
            return;
        }
        String report = generateReport();
        System.out.println(report);
    }

    public void searchTasks(String str) {
        List<Task> filteredTasks = tasks.stream()
                                        .filter(t -> t.getDescription().contains(str))
                                        .toList();
        if (filteredTasks.isEmpty()) {
            System.out.println("По фильтру задач не найдено");
            return;
        }

        StringBuilder result = new StringBuilder("Найденные задачи:\n");

        for (int i = 0; i < filteredTasks.size(); i++) {
            result.append(generateTaskForReport(i, filteredTasks));
        }

        System.out.println(result);
    }

    public void showCompletedTasks() {
        List<Task> completedTasks = tasks.stream()
                .filter(Task::getIsCompleted)
                .toList();

        if (completedTasks.isEmpty()) {
            System.out.println("Выполненных задач не найдено");
            return;
        }

        StringBuilder result = new StringBuilder("Выполненные задачи:\n");

        for (int i = 0; i < completedTasks.size(); i++) {
            result.append(generateTaskForReport(i, completedTasks));
        }

        System.out.println(result);
    }

    public void showPendingTasks() {
        List<Task> pendingTasks = tasks.stream()
                .filter(t -> !t.getIsCompleted())
                .toList();

        if (pendingTasks.isEmpty()) {
            System.out.println("Невыполненных задач не найдено");
            return;
        }

        StringBuilder result = new StringBuilder("Невыполненные задачи:\n");

        for (int i = 0; i < pendingTasks.size(); i++) {
            result.append(generateTaskForReport(i, pendingTasks));
        }

        System.out.println(result);
    }

    private String generateReport() {
        StringBuilder result = new StringBuilder("=== Список задач ===\n");
        for (int i = 0; i < tasks.size(); i++) {
            result.append(generateTaskForReport(i, tasks));
        }
        result.append("=== Конец отчёта ===");

        return result.toString();
    }

    private StringBuilder generateTaskForReport(int i, List<Task> tasks) {
        StringBuilder sb = new StringBuilder();
        Task task = tasks.get(i);
        int orderNum = i + 1;
        String status = task.getIsCompleted() ? "[x]" : "[ ]";

        sb.append(orderNum).append(". ")
                .append(task.id)
                .append(" ")
                .append(status)
                .append(" ")
                .append(task.getDescription())
                .append(", ")
                .append(task.getCategory())
                .append("\n");

       return sb;
    }
}
