package smartTaskManager.service;

import smartTaskManager.model.ScheduledTask;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.*;

public class TaskScheduler {
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public void scheduleReminder(ScheduledTask task, Runnable action) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime when = task.getScheduledTime();
        long delay = Math.max(0, Duration.between(now, when).toMillis());
        scheduler.schedule(action, delay, TimeUnit.MILLISECONDS);
    }

    public void shutdown() {
        scheduler.shutdown();
    }
}

