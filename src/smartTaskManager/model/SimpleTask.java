package smartTaskManager.model;

public final class SimpleTask extends Task implements Prioritizable {
    private static final long serialVersionUID = 1L;
    private final int priorityLevel;

    public SimpleTask(String description, int priorityLevel) {
        super(description);
        this.priorityLevel = priorityLevel;
    }

    @Override
    public int getPriorityLevel() { return priorityLevel; }
}
