package com.example.ismagilov.roman;

public class Task {
    private boolean isCompleted = false;
    private String description;

    public Task(String value) {
        this.description = value;
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
}
