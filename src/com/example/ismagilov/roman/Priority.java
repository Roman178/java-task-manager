package com.example.ismagilov.roman;

public enum Priority {
    LOW("LOW"),
    MEDIUM("MEDIUM"),
    HIGH("HIGH");

    private final String priorityLevel;

    Priority(String priorityLevel) {
        this.priorityLevel = priorityLevel;
    }

    public String getPriorityLevel() {
        return priorityLevel;
    }

    public static Priority fromString(String priorityLevel) {
        for (Priority prt : Priority.values()) {
            if (prt.getPriorityLevel().equals(priorityLevel)) {
                return prt;
            }
        }
        return null;
    }
}
