package edu.course.eventplanner;

import edu.course.eventplanner.model.Guest;
import edu.course.eventplanner.model.Task;
import edu.course.eventplanner.service.GuestListManager;
import edu.course.eventplanner.service.TaskManager;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    @Test
    void addGuestAddsGuestToManager() {
        GuestListManager manager = new GuestListManager();

        manager.addGuest(new Guest("Alice", "Family"));

        assertEquals(1, manager.getAllGuests().size());
        assertEquals("Alice", manager.getAllGuests().get(0).getName());
    }

    @Test
    void removeGuestRemovesExistingGuest() {
        GuestListManager manager = new GuestListManager();
        manager.addGuest(new Guest("Bob", "Friends"));

        boolean removed = manager.removeGuest("Bob");

        assertTrue(removed);
        assertTrue(manager.getAllGuests().isEmpty());
    }

    @Test
    void executeNextTaskMovesTaskToCompleted() {
        TaskManager manager = new TaskManager();
        manager.addTask(new Task("Decorate"));

        manager.executeNextTask();

        assertEquals("Decorate", manager.getCompleted().getDescription());
        assertEquals(0, manager.remainingTaskCount());
    }

    @Test
    void undoLastTaskRestoresTask() {
        TaskManager manager = new TaskManager();
        manager.addTask(new Task("Setup"));

        manager.executeNextTask();
        manager.undoLastTask();

        assertEquals(1, manager.remainingTaskCount());
        assertNull(manager.getCompleted());
    }
}

