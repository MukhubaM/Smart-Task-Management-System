package smartTaskManager.service;

import smartTaskManager.model.Task;
import smartTaskManager.model.User;
import smartTaskManager.util.TaskFilter;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface TaskService {
    User addUser(String name);
    Task addTask(Task task);
    Optional<User> findUserById(int id);
    Optional<Task> findTaskById(int id);
    void assignTask(Task task, User user);
    List<Task> getAllTasks();
    List<Task> filterTasks(TaskFilter filter);
    void processAllTasksConcurrently();
    void shutdownWorkers();
    Map<String, Long> countTasksByType();
}
