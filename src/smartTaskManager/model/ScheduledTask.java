package smartTaskManager.model;

import java.time.LocalDateTime;

public final class ScheduledTask extends Task implements Prioritizable {
    private static final long serialVersionUID = 1L;
    private final int priorityLevel;
    private final LocalDateTime scheduledTime;

    public ScheduledTask(String description, int priorityLevel, LocalDateTime scheduledTime) {
        super(description);
        this.priorityLevel = priorityLevel;
        this.scheduledTime = scheduledTime;
    }

    @Override
    public int getPriorityLevel() { return priorityLevel; }

    public LocalDateTime getScheduledTime() { return scheduledTime; }

    @Override
    public String toString() {
        return super.toString() + ", scheduledTime=" + scheduledTime;
    }
}

