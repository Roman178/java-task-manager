package com.example.ismagilov.roman;

import java.util.UUID;

public class Task {
    public UUID id = UUID.randomUUID();

    private static int taskCounter;

    private boolean isCompleted = false;
    private String description = "Новая задача";
    private String category = "Общие";

    public Task() {
        taskCounter++;
    }

    public Task(String description) {
        this();
        this.description = description;
    }

    public Task(String description, boolean isCompleted) {
        this(description);
        this.isCompleted = isCompleted;
    }

    public Task(String description, boolean isCompleted, String category) {
        this(description, isCompleted);
        this.category = category;
    }

    public static int getTaskCounter() {
        return taskCounter;
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

    public void setCategory(String category) {
        this.category = category;
    }

    public void updateAll(String[] parameters) {
        switch (parameters.length) {
            case 2: {
                setDescription(parameters[1]);
                break;
            }
            case 3: {
                boolean isCompleted = parameters[2].equals("1");
                setDescription(parameters[1]);
                setIsCompleted(isCompleted);
                break;
            }
            case 4: {
                boolean isCompleted = Integer.parseInt(parameters[2]) == 1;
                setDescription(parameters[1]);
                setIsCompleted(isCompleted);
                setCategory(parameters[3]);
                break;
            }
        }
    }
}
