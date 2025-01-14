package com.example.ismagilov.roman;

public enum CLICommands {
    ADD("add"),
    EDIT("edit"),
    REMOVE("remove"),
    SHOW_TASKS("show tasks"),
    SHOW_COMPLETED_TASKS("show completed tasks"),
    SHOW_PENDING_TASKS("show pending tasks"),
    SEARCH_TASKS("search tasks"),
    EXIT("exit"),
    SHOW_TASKS_BY_PRIORITY("show tasks by priority");

    private final String commandText;

    CLICommands(String commandText) {
        this.commandText = commandText;
    }

    public String getCommandText() {
        return commandText;
    }

    public static CLICommands fromString(String command) {
        for (CLICommands cmnd : CLICommands.values()) {
            if (cmnd.getCommandText().equals(command)) {
                return cmnd;
            }
        }
        return null;
    }
}
