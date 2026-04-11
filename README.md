# Smart Task Management System

A console-based Java application designed to help teams organize, assign and track work items.  
This project was developed as a capstone to demonstrate both foundational (OCA-level) and advanced (OCP-level) Java concepts, including object-oriented design, functional programming, concurrency, persistence and reporting.

---

##  Features

- **User Management**
  - Add and list users
  - Assign tasks to users

- **Task Management**
  - Create tasks (`SimpleTask`, `ScheduledTask`)
  - Track task priority and scheduling
  - Display all tasks

- **Advanced Design**
  - `sealed` class hierarchy for tasks
  - `record` for immutable `User`
  - `Prioritizable` interface for priority handling

- **Functional Programming**
  - Streams and lambdas for filtering and reporting
  - Custom `TaskFilter` functional interface

- **Concurrency**
  - Thread-safe `TaskManager` using concurrent collections
  - `ExecutorService` for parallel task processing
  - `ScheduledExecutorService` for reminders

- **Persistence**
  - Save and load data via Java serialization
  - Export tasks to CSV for reporting

- **Reporting**
  - Console summaries
  - Task counts by type and per user

---

##  Technologies Used

- **Java 24+** (records, sealed classes, pattern matching)
- **Collections Framework**
- **Concurrency Utilities**
- **Java I/O & Serialization**
- **Streams & Functional Interfaces**

---

##  Project Structure

smartTaskManager/

├─ model/          # Domain classes (User, Task, SimpleTask, ScheduledTask, Priority)

├─ service/        # Core services (TaskManager, TaskService, TaskWorker, TaskScheduler)

├─ persistence/    # PersistenceManager for saving/loading

├─ reporting/      # ReportGenerator for summaries and CSV export

├─ util/           # Utilities (TaskFilter, TaskNotFoundException)

├─ test/           # TestRunner for basic validation

└─ app/            # Main CLI application


## Usage

When you run the application, you’ll see a menu:

=== Smart Task Management ===
1) Add user
2) Add task
3) Display all tasks
4) Export tasks to CSV
5) Process tasks concurrently
6) Save data
7) Load data
8) Task counts by type
9) Run basic tests
0) Exit


## Testing

Run the built-in smoke tests:

java -cp out smartTaskManager.test.TestRunner


## Learning Outcomes

This project demonstrates:

OCA-level fundamentals: classes, objects, constructors, collections, encapsulation.
OCP-level skills: functional programming, concurrency, sealed classes, records, persistence, modular design.


## Future Enhancements

Replace serialization with JSON or database persistence
Add REST API with Spring Boot
Implement authentication and role-based access
Expand reporting (PDF/Excel, dashboards)
Comprehensive unit and integration tests

## License

