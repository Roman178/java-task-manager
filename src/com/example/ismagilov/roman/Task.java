package com.example.ismagilov.roman;

import java.util.*;

public class Task {
    public static Map<String, String> dictionary = new HashMap<>();
    private static int taskCounter;

    public UUID id = UUID.randomUUID();

    private String description = "Новая задача";
    private boolean isCompleted = false;
    private String category = "Общие";
    private Priority priority = Priority.MEDIUM;

    static {
        dictionary.put("description", "Описание");
        dictionary.put("isCompleted", "Завершено/Не завершено");
        dictionary.put("category", "Категория");
        dictionary.put("priority", "Приоритет");
    }

    public Task() {
        taskCounter++;
    }

    public boolean getIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String value) {
        this.description = value;
    }

    public String getCategory() {
        return category;
    }

    private void setCategory(String category) {
        this.category = category;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority value) {
        this.priority = value;
    }


    public void updateByParameters(String[] parameters) {
        for (int i = 0; i < parameters.length; i++) {
            switch (i) {
                case 0:
                    setDescription(parameters[i]);
                    break;
                case 1:
                    setIsCompleted(handleIsCompletedParameter(parameters[i]));
                    break;
                case 2:
                    setCategory(parameters[i]);
                    break;
                case 3:
                    setPriority(Priority.fromString(parameters[i]));
                    break;
            }
        }
    }

    private boolean handleIsCompletedParameter(String isCompletedCommand) {
        if (isCompletedCommand.equals("1")) {
            return true;
        }

        if (isCompletedCommand.equals("0")) {
            return false;
        }

        return isCompleted;
    }

    public List<String> checkParameters(String[] parameters) {
        List<String> res = new ArrayList<>();

        for (int index = 0; index < parameters.length; index++) {
            switch (index) {
                case 1:
                    String isCompletedStr = parameters[index];
                    if (!(isCompletedStr.equals("1") || isCompletedStr.equals("0"))) {
                        res.add("isCompleted");
                    }
                    break;
                case 3:
                    String priorityStr = parameters[index];
                    if (Priority.fromString(priorityStr) == null) {
                        res.add("priority");
                    }
                    break;
                default:
                    break;
            }
        }

        return res;
    }

    public static int getTaskCounter() {
        return taskCounter;
    }
}
