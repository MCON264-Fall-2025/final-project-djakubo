package edu.course.eventplanner;

import edu.course.eventplanner.model.Task;
import edu.course.eventplanner.service.TaskManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class TaskManagerTest {
    private final Task task1 = new Task("Say hello to family members");
    private final Task task2 = new Task("Say goodbye to family members");
    private final TaskManager taskManager = new TaskManager();


    @BeforeEach
    void addTasks(){
        taskManager.addTask(task1);
        taskManager.addTask(task2);
    }

    @Test
    void testAddTask(){
        assertEquals(task1, taskManager.getUpcoming());
    }

    @Test
    void testExecuteNextTask(){
        assertEquals(task1, taskManager.executeNextTask());
        assertEquals(task2, taskManager.getUpcoming());
        assertEquals(task1, taskManager.getCompleted());
    }

    @Test
    void testUndoLastTask(){
        taskManager.executeNextTask();
        assertEquals(task1, taskManager.undoLastTask());
        assertEquals(task2, taskManager.getUpcoming());
        assertNull(taskManager.getCompleted());

        taskManager.executeNextTask();
        assertEquals(task1, taskManager.getUpcoming());
    }

    @Test
    void testRemainingTaskCount(){
        assertEquals(2, taskManager.remainingTaskCount());
        taskManager.executeNextTask();
        assertEquals(1, taskManager.remainingTaskCount());
    }

    @Test
    public void testMixedExecuteAndUndo() {
        taskManager.addTask(new Task("Task 3"));

        taskManager.executeNextTask(); // Execute Task 1
        taskManager.executeNextTask(); // Execute Task 2

        taskManager.undoLastTask(); // Undo Task 2

        assertEquals(1, taskManager.remainingTaskCount(), "Should have Task 3 remaining");

        Task next = taskManager.executeNextTask(); // Should execute Task 3
        assertEquals("Task 3", next.getDescription());
    }
}
