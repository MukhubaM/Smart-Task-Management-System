package smartTaskManager.test;

import smartTaskManager.model.SimpleTask;
import smartTaskManager.model.Task;
import smartTaskManager.model.User;
import smartTaskManager.service.TaskManager;

public class TestRunner {
    public static void runAll() {
        System.out.println("Running basic tests...");
        TaskManager manager = new TaskManager();
        User u1 = manager.addUser("TestUser1");
        Task t1 = new SimpleTask("Test Task", 2);
        manager.addTask(t1);
        manager.assignTask(t1, u1);
        if (t1.getAssignedUser() == null) {
            throw new AssertionError("Task assignment failed");
        }
        System.out.println("Basic tests passed.");
    }
}

