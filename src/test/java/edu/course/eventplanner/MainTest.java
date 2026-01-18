package edu.course.eventplanner;

import edu.course.eventplanner.model.Task;
import edu.course.eventplanner.service.GuestListManager;
import edu.course.eventplanner.service.TaskManager;
import edu.course.eventplanner.service.VenueSelector;
import edu.course.eventplanner.util.Generators;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    @Test
    void loadSampleDataReturnsVenue() {
        VenueSelector selector = new VenueSelector(Generators.generateVenues());
        assertNotNull(Main.loadSampleData(selector));
    }

    @Test
    void addGuestAddsGuest() {
        GuestListManager manager = new GuestListManager();
        Scanner scanner = new Scanner(new ByteArrayInputStream("Alice\nFamily\n".getBytes()));

        Main.addGuest(scanner, manager);

        assertEquals(1, manager.getAllGuests().size());
    }

    @Test
    void removeGuestRemovesExistingGuest() {
        GuestListManager manager = new GuestListManager();
        manager.addGuest(new edu.course.eventplanner.model.Guest("Bob", "Friends"));
        Scanner scanner = new Scanner(new ByteArrayInputStream("Bob\n".getBytes()));

        Main.removeGuest(scanner, manager);

        assertTrue(manager.getAllGuests().isEmpty());
    }

    @Test
    void addTaskAddsTask() {
        TaskManager manager = new TaskManager();
        Scanner scanner = new Scanner(new ByteArrayInputStream("Decorate\n".getBytes()));

        Main.addTask(scanner, manager);

        assertEquals(1, manager.remainingTaskCount());
    }

    @Test
    void executeNextTaskExecutesTask() {
        TaskManager manager = new TaskManager();
        manager.addTask(new Task("Setup"));

        Main.executeNextTask(manager);

        assertNotNull(manager.getCompleted());
    }

    @Test
    void undoLastTaskRestoresTask() {
        TaskManager manager = new TaskManager();
        manager.addTask(new Task("Setup"));
        manager.executeNextTask();

        Main.undoLastTask(manager);

        assertEquals(1, manager.remainingTaskCount());
    }
}
