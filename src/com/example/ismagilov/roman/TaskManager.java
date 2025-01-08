package com.example.ismagilov.roman;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TaskManager {
    private final List<Task> tasks = new ArrayList<>();

    public void addTask(String task) {
        tasks.add(new Task(task));
    }

    public void removeTask(int index) {
        if (isIndexInvalid(index)) {
            System.out.println("Invalid index when remove: " + index);
            return;
        }

        tasks.remove(index);
    }

    public void markTaskAsCompleted(int index) {
        if (isIndexInvalid(index)) {
            System.out.println("Invalid index when mark task as completed: " + index);
            return;
        }

        Task task = tasks.get(index);
        task.setIsCompleted(true);
    }

    public void editTask(int index, String newDescription) {
        if (isIndexInvalid(index)) {
            System.out.println("Invalid index when edit: " + index);
            return;
        }

        Task task = tasks.get(index);
        task.setDescription(newDescription);
    }

    public void showTasks() {
        String report = generateReport();
        System.out.println(report);
    }

    public void searchTasks(String str) {
        List<Task> filteredTasks = tasks.stream()
                                        .filter(t -> t.getDescription().contains(str))
                                        .toList();

        StringBuilder result = new StringBuilder("Найденные задачи:\n");

        for (int i = 0; i < filteredTasks.size(); i++) {
            result.append(generateTaskForReport(i, filteredTasks));
        }

        System.out.println(result);
    }

    public void showCompletedTasks() {
        List<Task> completedTasks = tasks.stream()
                .filter(t -> t.getIsCompleted())
                .toList();

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

        StringBuilder result = new StringBuilder("Невыполненные задачи:\n");

        for (int i = 0; i < pendingTasks.size(); i++) {
            result.append(generateTaskForReport(i, pendingTasks));
        }

        System.out.println(result);
    }

    public void addTaskInteractively() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите описание новой задачи");
        String str = scanner.nextLine();

        this.addTask(str);

        System.out.println("Задача " + "'" + str + "'" + " добавлена");
    }

    private boolean isIndexInvalid(int index) {
        return index < 0 || index >= tasks.size();
    }

    private String generateReport() {
        if (tasks.isEmpty()) {
            return "Список задач пуст";
        }

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
                .append(status)
                .append(" ")
                .append(task.getDescription())
                .append("\n");

       return sb;
    }
}
