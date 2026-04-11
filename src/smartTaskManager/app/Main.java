package smartTaskManager.app;

import smartTaskManager.model.*;
import smartTaskManager.persistence.PersistenceManager;
import smartTaskManager.reporting.ReportGenerator;
import smartTaskManager.service.TaskManager;
import smartTaskManager.service.TaskScheduler;
import smartTaskManager.test.TestRunner;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
    private static final String DATA_FILE = "task_data.ser";

    public static void main(String[] args) {
        TaskManager manager = new TaskManager();
        TaskScheduler scheduler = new TaskScheduler();

        // Starter data
        User alice = manager.addUser("Alice");
        User bob = manager.addUser("Bob");

        Task t1 = new SimpleTask("Prepare project report", Priority.HIGH.level());
        Task t2 = new ScheduledTask("Fix bugs in module", Priority.MEDIUM.level(), LocalDateTime.now().plusSeconds(10));
        manager.addTask(t1);
        manager.addTask(t2);
        manager.assignTask(t1, alice);
        manager.assignTask(t2, bob);

        if (t2 instanceof ScheduledTask st) {
            scheduler.scheduleReminder(st, () -> System.out.println("Reminder: " + st.getDescription() + " scheduled at " + st.getScheduledTime()));
        }

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            printMenu();
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1" -> {
                    System.out.print("Enter user name: ");
                    String name = scanner.nextLine().trim();
                    User u = manager.addUser(name);
                    System.out.println("Added user: " + u);
                }
                case "2" -> {
                    System.out.print("Task type (1=Simple,2=Scheduled): ");
                    String type = scanner.nextLine().trim();
                    System.out.print("Description: ");
                    String desc = scanner.nextLine().trim();
                    System.out.print("Priority (1=LOW,2=MEDIUM,3=HIGH): ");
                    int p = Integer.parseInt(scanner.nextLine().trim());
                    if ("1".equals(type)) {
                        Task t = new SimpleTask(desc, p);
                        manager.addTask(t);
                        System.out.println("Added: " + t);
                    } else {
                        System.out.print("Scheduled time (YYYY-MM-DDTHH:MM): ");
                        String when = scanner.nextLine().trim();
                        LocalDateTime dt = LocalDateTime.parse(when);
                        Task t = new ScheduledTask(desc, p, dt);
                        manager.addTask(t);
                        System.out.println("Added: " + t);
                    }
                }
                case "3" -> {
                    List<Task> tasks = manager.getAllTasks();
                    ReportGenerator.printTaskSummary(tasks);
                }
                case "4" -> {
                    System.out.print("Export CSV filename: ");
                    String fn = scanner.nextLine().trim();
                    try {
                        ReportGenerator.exportTasksToCsv(manager.getAllTasks(), fn);
                        System.out.println("Exported to " + fn);
                    } catch (IOException e) {
                        System.err.println("Export failed: " + e.getMessage());
                    }
                }
                case "5" -> {
                    System.out.println("Processing tasks concurrently...");
                    manager.processAllTasksConcurrently();
                }
                case "6" -> {
                    System.out.print("Save data filename (or press enter for default): ");
                    String fn = scanner.nextLine().trim();
                    if (fn.isEmpty()) fn = DATA_FILE;
                    try {
                        PersistenceManager.saveObject(fn, manager);
                        System.out.println("Saved to " + fn);
                    } catch (IOException e) {
                        System.err.println("Save failed: " + e.getMessage());
                    }
                }
                case "7" -> {
                    System.out.print("Load data filename (or press enter for default): ");
                    String fn = scanner.nextLine().trim();
                    if (fn.isEmpty()) fn = DATA_FILE;
                    try {
                        Object obj = PersistenceManager.loadObject(fn);
                        if (obj instanceof TaskManager tm) {
                            manager = tm;
                            System.out.println("Loaded data from " + fn);
                        } else {
                            System.err.println("File did not contain TaskManager data.");
                        }
                    } catch (IOException | ClassNotFoundException e) {
                        System.err.println("Load failed: " + e.getMessage());
                    }
                }
                case "8" -> {
                    Map<String, Long> counts = manager.countTasksByType();
                    System.out.println("Task counts by type: " + counts);
                }
                case "9" -> {
                    System.out.println("Running tests...");
                    TestRunner.runAll();
                }
                case "0" -> {
                    System.out.println("Shutting down...");
                    running = false;
                }
                default -> System.out.println("Unknown option.");
            }
        }

        manager.shutdownWorkers();
        scheduler.shutdown();
        scanner.close();
        System.out.println("Goodbye.");
    }

    private static void printMenu() {
        System.out.println("\n=== Smart Task Management ===");
        System.out.println("1) Add user");
        System.out.println("2) Add task");
        System.out.println("3) Display all tasks");
        System.out.println("4) Export tasks to CSV");
        System.out.println("5) Process tasks concurrently");
        System.out.println("6) Save data");
        System.out.println("7) Load data");
        System.out.println("8) Task counts by type");
        System.out.println("9) Run basic tests");
        System.out.println("0) Exit");
        System.out.print("Choose an option: ");
    }
}
