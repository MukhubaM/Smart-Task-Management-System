package smartTaskManager.service;

import smartTaskManager.model.Task;

public class TaskWorker implements Runnable {
    private final Task task;

    public TaskWorker(Task task) { this.task = task; }

    @Override
    public void run() {
        System.out.println("Processing task: " + task.getDescription() + " assigned to " + (task.getAssignedUser() == null ? "unassigned" : task.getAssignedUser().name()));
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("Completed task: " + task.getDescription());
    }
}
