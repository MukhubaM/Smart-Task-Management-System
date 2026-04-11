package smartTaskManager.service;

import smartTaskManager.model.Task;
import smartTaskManager.model.User;
import smartTaskManager.util.TaskFilter;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * Thread-safe TaskManager. Serializable; transient non-serializable fields are reinitialized after deserialization.
 */
public class TaskManager implements TaskService, Serializable {
    private static final long serialVersionUID = 1L;

    private final ConcurrentMap<Integer, User> users = new ConcurrentHashMap<>();
    private final ConcurrentMap<Integer, Task> tasks = new ConcurrentHashMap<>();
    private final AtomicInteger userIdGen = new AtomicInteger(1);
    private final AtomicInteger taskIdGen = new AtomicInteger(1);

    // Not serializable: mark transient and recreate after deserialization
    private transient ExecutorService workerPool = Executors.newFixedThreadPool(4);

    public TaskManager() {
        // workerPool already initialized above; constructor kept for clarity
    }

    @Override
    public User addUser(String name) {
        int id = userIdGen.getAndIncrement();
        User u = new User(name, id);
        users.put(id, u);
        return u;
    }

    @Override
    public Task addTask(Task task) {
        int id = taskIdGen.getAndIncrement();
        tasks.put(id, task);
        return task;
    }

    @Override
    public Optional<User> findUserById(int id) {
        return Optional.ofNullable(users.get(id));
    }

    @Override
    public Optional<Task> findTaskById(int id) {
        return Optional.ofNullable(tasks.get(id));
    }

    @Override
    public void assignTask(Task task, User user) {
        synchronized (task) {
            task.assignUser(user);
        }
    }

    @Override
    public List<Task> getAllTasks() {
        return tasks.values().stream().collect(Collectors.toList());
    }

    @Override
    public List<Task> filterTasks(TaskFilter filter) {
        return tasks.values().stream().filter(filter::test).collect(Collectors.toList());
    }

    @Override
    public void processAllTasksConcurrently() {
        for (Task t : getAllTasks()) {
            workerPool.submit(new TaskWorker(t));
        }
    }

    @Override
    public void shutdownWorkers() {
        workerPool.shutdown();
    }

    @Override
    public Map<String, Long> countTasksByType() {
        return tasks.values().stream().collect(Collectors.groupingBy(t -> t.getClass().getSimpleName(), Collectors.counting()));
    }

    /**
     * Ensure transient fields are reinitialized after deserialization.
     */
    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
        this.workerPool = Executors.newFixedThreadPool(4);
    }
}

