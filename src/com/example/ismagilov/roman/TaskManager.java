package com.example.ismagilov.roman;

import java.util.ArrayList;
import java.util.List;

public class TaskManager {
    List<String> tasks = new ArrayList<>();
    List<Boolean> isCompleted = new ArrayList<>();

    public void addTask(String task) {
        tasks.add(task);
        isCompleted.add(false);
    }

    public void removeTask(int index) {
        if (isIndexInvalid(index)) {
            System.out.println("Invalid index when remove: " + index);
            return;
        }

        tasks.remove(index);
        isCompleted.remove(index);
    }

    public void markTaskAsCompleted(int index) {
        if (isIndexInvalid(index)) {
            System.out.println("Invalid index when mark task as completed: " + index);
            return;
        }

        isCompleted.set(index, true);
    }

    public void editTask(int index, String newTask) {
        if (isIndexInvalid(index)) {
            System.out.println("Invalid index when edit: " + index);
            return;
        }

        tasks.set(index, newTask);
    }

    public void showTasks() {
        String report = generateReport();
        System.out.println(report);
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
            StringBuilder innerSb = new StringBuilder();
            int orderNum = i + 1;
            String status = isCompleted.get(i) ? "[x]" : "[ ]";

            innerSb.append(orderNum).append(". ").append(status).append(" ").append(tasks.get(i)).append("\n");

            result.append(innerSb);
        }
        result.append("=== Конец отчёта ===");

        return result.toString();
    }
}
