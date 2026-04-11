package smartTaskManager.reporting;

import smartTaskManager.model.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ReportGenerator {
    public static void printTaskSummary(List<Task> tasks) {
        System.out.println("=== Task Summary ===");
        for (Task t : tasks) {
            System.out.println(t);
        }
    }

    public static void exportTasksToCsv(List<Task> tasks, String filename) throws IOException {
        try (FileWriter fw = new FileWriter(filename)) {
            fw.write("Type, Description, AssignedUser, Priority, ScheduledTime\n");
            for (Task t : tasks) {
                String type = t.getClass().getSimpleName();
                String desc = t.getDescription().replace(", ", " ");
                String user = t.getAssignedUser() == null ? "" : t.getAssignedUser().name();
                String priority = "";
                String scheduled = "";
                if (t instanceof Prioritizable p) {
                    priority = String.valueOf(p.getPriorityLevel());
                }
                if (t instanceof ScheduledTask st) {
                    scheduled = st.getScheduledTime().toString();
                }
                fw.write(String.join(",", type, desc, user, priority, scheduled) + "\n");
            }
        }
    }

    public static void printTasksPerUser(List<Task> tasks) {
        Map<String, Long> counts = tasks.stream()
                .filter(t -> t.getAssignedUser() != null)
                .collect(Collectors.groupingBy(t -> t.getAssignedUser().name(), Collectors.counting()));
        System.out.println("=== Tasks per User ===");
        counts.forEach((user, count) -> System.out.println(user + ": " + count));
    }
}
