package smartTaskManager.model;

import java.io.Serializable;

public sealed abstract class Task implements Serializable permits SimpleTask, ScheduledTask {
    private static final long serialVersionUID = 1L;
    private final String description;
    private User assignedUser;

    public Task(String description) {
        this.description = description;
    }

    public String getDescription() { return description; }

    public User getAssignedUser() { return assignedUser; }

    public void assignUser(User user) { this.assignedUser = user; }

    @Override
    public String toString() {
        String user = assignedUser == null ? "unassigned" : assignedUser.name();
        return String.format("%s{description='%s', assignedUser=%s}", getClass().getSimpleName(), description, user);
    }
}
