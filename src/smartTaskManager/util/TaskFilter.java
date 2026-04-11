package smartTaskManager.util;

import smartTaskManager.model.Task;

@FunctionalInterface
public interface TaskFilter {
    boolean test(Task task);
}
