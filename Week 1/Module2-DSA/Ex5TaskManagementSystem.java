import java.util.Scanner;

class Task {
    private final int taskId;
    private String taskName;
    private Status status;

    enum Status {
        READY, RUNNING, WAITING, COMPLETED
    }

    Task(int taskId, String taskName, Status status) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.status = status;
    }

    public int getTaskId() {
        return taskId;
    }

    public void getTaskInfo() {
        System.out.println();
        System.out.println("Task Id: " + taskId);
        System.out.println("Task Name: " + taskName);
        System.out.println("Status: " + status);
        System.out.println();
    }
}

class TaskNode {
    public Task task;
    public TaskNode nextTask;

    TaskNode(Task task) {
        this.task = task;
        this.nextTask = null;
    }
}

class TaskManager {
    private TaskNode headTask = null;

    public boolean isEmpty() {

        if (headTask == null) {
            System.out.println("No tasks in the list");
            return true;
        }

        return false;
    }

    public void addTaskFirst(TaskNode task) {
        if (headTask == null) {
            headTask = task;
            return;
        }

        task.nextTask = headTask;
        headTask = task;
    }

    public void addTaskLast(TaskNode task) {
        if (headTask == null) {
            headTask = task;
        } else {
            TaskNode temp = headTask;
            while (temp.nextTask != null) {
                temp = temp.nextTask;
            }
            temp.nextTask = task;
        }
    }

    public boolean addTaskAtIndex(TaskNode task, int index) {
        if (index == 0) {
            addTaskFirst(task);
            return true;
        }

        TaskNode temp = headTask;
        int count = 0;

        while (temp != null && count < index - 1) {
            temp = temp.nextTask;
            count++;
        }

        if (temp == null || index < 0) {
            return false;
        }

        task.nextTask = temp.nextTask;
        temp.nextTask = task;
        return true;
    }

    public int search(int taskId) {

        if (isEmpty()) {
            return -1;
        }

        TaskNode temp = headTask;
        int index = -1;

        while (temp != null) {
            index++;
            if (temp.task.getTaskId() == taskId) {
                return index;
            }

            temp = temp.nextTask;
        }

        return -1;
    }

    public void traverse() {

        if (isEmpty()) {
            return;
        }

        TaskNode temp = headTask;

        while (temp != null) {
            temp.task.getTaskInfo();
            temp = temp.nextTask;
        }
    }

    public void deleteTaskFirst() {
        if (isEmpty()) {
            return;
        }

        headTask = headTask.nextTask;
    }

    public void deleteTaskLast() {
        if (isEmpty()) {
            return;
        }

        if (headTask.nextTask == null) {
            headTask = null;
            return;
        }

        TaskNode temp = headTask;

        while (temp.nextTask.nextTask != null) {
            temp = temp.nextTask;
        }

        temp.nextTask = null;
    }

    public boolean deleteTaskAtIndex(int index) {
        if (index == 0) {
            deleteTaskFirst();
            return true;
        }

        if (isEmpty()) {
            return false;
        }

        TaskNode temp = headTask;
        int count = 0;

        while (temp.nextTask != null && count < index - 1) {
            temp = temp.nextTask;
            count++;
        }

        if (temp.nextTask == null || index < 0) {
            return false;
        }

        temp.nextTask = temp.nextTask.nextTask;
        return true;
    }

}

public class Ex5TaskManagementSystem {
    private static Scanner sc = new Scanner(System.in);
    private static TaskManager taskManager = new TaskManager();

    public static void main(String[] args) {
        int choice;
        do {
            System.out.println("\n=============================================\n");
            System.out.println("Task Management System Menu:");
            System.out.println("\n=============================================\n");
            System.out.println("1. Add Task at First");
            System.out.println("2. Add Task at Last");
            System.out.println("3. Add Task at Index");
            System.out.println("4. Display All Tasks");
            System.out.println("5. Search Task by ID");
            System.out.println("6. Delete First Task");
            System.out.println("7. Delete Last Task");
            System.out.println("8. Delete Task at Index");
            System.out.println("9. Exit");
            System.out.println();
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            System.out.println();
            sc.nextLine();

            switch (choice) {
                case 1:
                    addTaskFirst();
                    break;
                case 2:
                    addTaskLast();
                    break;
                case 3:
                    addTaskAtIndex();
                    break;
                case 4:
                    displayAllTasks();
                    break;
                case 5:
                    searchTaskById();
                    break;
                case 6:
                    deleteFirstTask();
                    break;
                case 7:
                    deleteLastTask();
                    break;
                case 8:
                    deleteTaskAtIndex();
                    break;
                case 9:
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 9);

        sc.close();
        System.out.println("Exiting...");
    }

    private static void addTaskFirst() {
        Task newTask = createTask();
        TaskNode newNode = new TaskNode(newTask);
        taskManager.addTaskFirst(newNode);
        System.out.println("Task added at the beginning.");
    }

    private static void addTaskLast() {
        Task newTask = createTask();
        TaskNode newNode = new TaskNode(newTask);
        taskManager.addTaskLast(newNode);
        System.out.println("Task added at the end.");
    }

    private static void addTaskAtIndex() {
        Task newTask = createTask();
        TaskNode newNode = new TaskNode(newTask);
        System.out.print("Enter the index at which to add the task (0 for first): ");
        int index = sc.nextInt();
        boolean success = taskManager.addTaskAtIndex(newNode, index);
        if (success) {
            System.out.println("Task added at index " + index + ".");
        } else {
            System.out.println("Index out of range.");
        }
    }

    private static Task createTask() {
        System.out.print("Enter task ID: ");
        int taskId = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter task name: ");
        String taskName = sc.nextLine();

        System.out.print("Enter task status (READY, RUNNING, WAITING, COMPLETED): ");
        String statusStr = sc.nextLine().toUpperCase();
        Task.Status status = Task.Status.valueOf(statusStr);

        return new Task(taskId, taskName, status);
    }

    private static void displayAllTasks() {
        taskManager.traverse();
    }

    private static void searchTaskById() {
        System.out.print("Enter task ID to search: ");
        int taskId = sc.nextInt();
        int index = taskManager.search(taskId);

        if (index == -1) {
            System.out.println("Task not found.");
        } else {
            System.out.println("Task found at index: " + index);
        }
    }

    private static void deleteFirstTask() {
        taskManager.deleteTaskFirst();
        System.out.println("First task deleted successfully.");
    }

    private static void deleteLastTask() {
        taskManager.deleteTaskLast();
        System.out.println("Last task deleted successfully.");
    }

    private static void deleteTaskAtIndex() {
        System.out.print("Enter index to delete task: ");
        int index = sc.nextInt();
        boolean success = taskManager.deleteTaskAtIndex(index);
        if (success) {
            System.out.println("Task at index " + index + " deleted successfully.");
        } else {
            System.out.println("Index out of range.");
        }
    }
}
