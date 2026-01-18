package edu.course.eventplanner;

import edu.course.eventplanner.model.Guest;
import edu.course.eventplanner.model.Task;
import edu.course.eventplanner.model.Venue;
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

    @Test
    void selectVenueReturnsValidVenue() {
        VenueSelector selector = new VenueSelector(Generators.generateVenues());
        Scanner scanner = new Scanner(
                new ByteArrayInputStream("10000\n50\n".getBytes())
        );

        Venue venue = Main.selectVenue(scanner, selector);

        assertNotNull(venue);
    }

    @Test
    void generateSeatingChartRunsWithValidData() {
        GuestListManager guestManager = new GuestListManager();
        guestManager.addGuest(new Guest("A", "G"));
        guestManager.addGuest(new Guest("B", "G"));

        Venue venue = new Venue("Hall", 1000, 10, 5, 2);

        // method returns void — test that it does NOT throw
        assertDoesNotThrow(() ->
                Main.generateSeatingChart(venue, guestManager)
        );
    }

    @Test
    void printEventSummaryRunsWithValidData() {
        GuestListManager guestManager = new GuestListManager();
        guestManager.addGuest(new Guest("A", "G"));

        TaskManager taskManager = new TaskManager();
        taskManager.addTask(new edu.course.eventplanner.model.Task("Setup"));

        Venue venue = new Venue("Hall", 1000, 10, 5, 2);

        assertDoesNotThrow(() ->
                Main.printEventSummary(venue, guestManager, taskManager)
        );
    }



}
