package com.example.ismagilov.roman;

import java.util.*;

public class TaskManager {
    public static int maxTasks = 7;

    private List<Task> tasks = new ArrayList<>();

    public void handleEditingTask(String[] parametersWithId) {
        UUID id = UUID.fromString(parametersWithId[0]);
        String[] parameters = Arrays.copyOfRange(parametersWithId, 1, parametersWithId.length);
        Optional<Task> task = tasks.stream().filter(t -> t.id.equals(id)).findFirst();

        task.ifPresentOrElse(tsk -> {
                    updateTaskByParams(tsk, parameters, "Задача с id " + id + " обновлена");
                },
                () -> System.out.println("Задача с id " + id + " не найдена"));
    }

    private void updateTaskByParams(Task task, String[] parameters, String successMsg) {
        List<String> invalidParameters = task.checkParameters(parameters);
        if (invalidParameters.isEmpty()) {
            task.updateByParameters(parameters);
            System.out.println(successMsg);
        } else {
            List<String> readableParams = invalidParameters.stream().map(p -> Task.dictionary.get(p)).toList();
            String res = String.join(", ", readableParams);
            System.out.println("Введенные параметры невалидны: " + res);
        }
    }

    public void addTask(String... parameters) {
        if (tasks.size() < maxTasks) {
              Task task = new Task();
              updateTaskByParams(task, parameters, "Задача создана");
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
//        if (tasks.isEmpty()) {
//            System.out.println("Список задач пуст");
//            return;
//        }
        String report = generateReport("Задачи", "Список задач пуст", tasks);
        System.out.println(report);
    }

    public void searchTasks(String[] parameters) {
        if (parameters.length == 0) {
            System.out.println("Строка для фильтра не введена");
            return;
        }

        String str = parameters[0];
        List<Task> filteredTasks = tasks.stream()
                                        .filter(t -> t.getDescription().contains(str))
                                        .toList();

        String report = generateReport("Найденные задачи", "По фильтру задач не найдено", filteredTasks);
        System.out.println(report);
//        if (filteredTasks.isEmpty()) {
//            System.out.println("По фильтру задач не найдено");
//            return;
//        }
//
//        StringBuilder result = new StringBuilder("Найденные задачи:\n");
//
//        for (int i = 0; i < filteredTasks.size(); i++) {
//            result.append(generateTaskForReport(i, filteredTasks));
//        }
//
//        System.out.println(result);
    }

    public void showTasksByPriority(String[] parameters) {
        if (parameters.length == 0) {
            System.out.println("Не введен приоритет для вывода задач");
            return;
        }

        Priority priority = Priority.fromString(parameters[0]);

        if (priority == null) {
            System.out.println("Невалидная строка приоритета. Введите: LOW, MEDIUM или HIGH");
            return;
        }

        List<Task> filteredTasks = tasks.stream()
                .filter(t -> t.getPriority().equals(priority))
                .toList();

        String report = generateReport("Задачи с приоритетом " + parameters[0], "Задач с приоритетом " + parameters[0] + " не найдено", filteredTasks);
        System.out.println(report);
    }

    public void showCompletedTasks() {
        List<Task> completedTasks = tasks.stream()
                .filter(Task::getIsCompleted)
                .toList();

        String report = generateReport("Выполненные задачи: ","Выполненных задач не найдено", completedTasks);
        System.out.println(report);

//        if (completedTasks.isEmpty()) {
//            System.out.println("Выполненных задач не найдено");
//            return;
//        }
//
//        StringBuilder result = new StringBuilder("Выполненные задачи:\n");
//
//        for (int i = 0; i < completedTasks.size(); i++) {
//            result.append(generateTaskForReport(i, completedTasks));
//        }
//
//        System.out.println(result);
    }

    public void showPendingTasks() {
        List<Task> pendingTasks = tasks.stream()
                .filter(t -> !t.getIsCompleted())
                .toList();

        String report = generateReport("Не выполненные задачи: ","Не выполненных задач не найдено", pendingTasks);
        System.out.println(report);

//        if (pendingTasks.isEmpty()) {
//            System.out.println("Невыполненных задач не найдено");
//            return;
//        }
//
//        StringBuilder result = new StringBuilder("Невыполненные задачи:\n");
//
//        for (int i = 0; i < pendingTasks.size(); i++) {
//            result.append(generateTaskForReport(i, pendingTasks));
//        }
//
//        System.out.println(result);
    }

    private String generateReport(String titleOfList, String emptyListMsg, List<Task> listOfTasks) {
        if (listOfTasks.isEmpty()) {
            return emptyListMsg;
        }

        StringBuilder result = new StringBuilder("=== " + titleOfList + " ===\n");
        for (int i = 0; i < listOfTasks.size(); i++) {
            result.append(generateTaskForReport(i, listOfTasks));
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
