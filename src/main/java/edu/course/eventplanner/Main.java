package edu.course.eventplanner;

import edu.course.eventplanner.model.Guest;
import edu.course.eventplanner.model.Task;
import edu.course.eventplanner.model.Venue;
import edu.course.eventplanner.service.GuestListManager;
import edu.course.eventplanner.service.SeatingPlanner;
import edu.course.eventplanner.service.TaskManager;
import edu.course.eventplanner.service.VenueSelector;
import edu.course.eventplanner.util.Generators;

import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        GuestListManager guestListManager = new GuestListManager();
        TaskManager taskManager = new TaskManager();
        VenueSelector venueSelector = new VenueSelector(Generators.generateVenues());
        Venue venue = null;

        int choice = 1;
        String menu =
                "1. Load sample data\n" +
                        "2. Add guest\n" +
                        "3. Remove guest\n" +
                        "4. Select venue\n" +
                        "5. Generate seating chart\n" +
                        "6. Add preparation task\n" +
                        "7. Execute next task\n" +
                        "8. Undo last task\n" +
                        "9. Print event summary\n" +
                        "0. Exit";

        while (choice != 0) {
            System.out.println("\nEvent Planner Menu");
            System.out.println(menu);
            System.out.print("Enter choice: ");

            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number.");
                continue;
            }

            switch (choice) {
                case 1 -> venue = loadSampleData(venueSelector);
                case 2 -> addGuest(scanner, guestListManager);
                case 3 -> removeGuest(scanner, guestListManager);
                case 4 -> venue = selectVenue(scanner, venueSelector);
                case 5 -> generateSeatingChart(venue, guestListManager);
                case 6 -> addTask(scanner, taskManager);
                case 7 -> executeNextTask(taskManager);
                case 8 -> undoLastTask(taskManager);
                case 9 -> printEventSummary(venue, guestListManager, taskManager);
                case 0 -> System.out.println("Goodbye!");
                default -> System.out.println("Invalid choice.");
            }
        }

        scanner.close();
        System.exit(0);
    }

    /* ---------------- HELPER METHODS ---------------- */

    static Venue loadSampleData(VenueSelector venueSelector) {
        List<Guest> guests = Generators.GenerateGuests(100);
        Venue venue = venueSelector.selectVenue(10000, 100);
        SeatingPlanner seatingPlanner = new SeatingPlanner(venue);

        String seatingChart = seatingPlanner.generateSeating(guests)
                .toString().replace("],", "]\n");

        System.out.println("Budget: $10,000");
        System.out.println("GuestCount: 100");
        System.out.println("Venue: " + venue.getName());
        System.out.println("Seating Chart:\n" + seatingChart);

        return venue;
    }

    static void addGuest(Scanner scanner, GuestListManager manager) {
        System.out.print("Enter guest name: ");
        String name = scanner.nextLine();
        if (name.isEmpty()) {
            System.out.println("Guest name cannot be empty.");
            return;
        }

        System.out.print("Enter guest group tag: ");
        String groupTag = scanner.nextLine();
        if (groupTag.isEmpty()) {
            System.out.println("Group tag cannot be empty.");
            return;
        }

        manager.addGuest(new Guest(name, groupTag));
        System.out.println(name + " has been added to the guest list");
    }

    static void removeGuest(Scanner scanner, GuestListManager manager) {
        System.out.print("Enter guest name to remove: ");
        String name = scanner.nextLine();

        if (!manager.removeGuest(name)) {
            System.out.println("No such guest found");
        } else {
            System.out.println(name + " was removed");
        }
    }

    static Venue selectVenue(Scanner scanner, VenueSelector selector) {
        while (true) {
            try {
                System.out.print("Enter your budget: ");
                double budget = Double.parseDouble(scanner.nextLine());

                System.out.print("Enter number of guests: ");
                int guestCount = Integer.parseInt(scanner.nextLine());

                Venue venue = selector.selectVenue(budget, guestCount);
                if (venue == null) {
                    System.out.println("No venue fits that budget and guest count.");
                    continue;
                }

                System.out.println("Venue: " + venue.getName());
                return venue;

            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter numbers only.");
            }
        }
    }

    static void generateSeatingChart(Venue venue, GuestListManager manager) {
        if (venue == null) {
            System.out.println("Select venue first");
            return;
        }
        if (manager.getAllGuests().isEmpty()) {
            System.out.println("Add guests first");
            return;
        }

        SeatingPlanner planner = new SeatingPlanner(venue);
        String chart = planner.generateSeating(manager.getAllGuests())
                .toString().replace("],", "]\n");

        System.out.println("Seating Chart:\n" + chart);
    }

    static void addTask(Scanner scanner, TaskManager manager) {
        System.out.print("Enter task: ");
        Task task = new Task(scanner.nextLine());
        manager.addTask(task);
        System.out.println(task.getDescription() + " has been added to the task list");
    }

    static void executeNextTask(TaskManager manager) {
        if (manager.remainingTaskCount() == 0) {
            System.out.println("No tasks to be completed");
            return;
        }

        manager.executeNextTask();
        System.out.println(manager.getCompleted().getDescription() + " was executed");
    }

    static void undoLastTask(TaskManager manager) {
        if (manager.getCompleted() == null) {
            System.out.println("No task was completed");
            return;
        }

        System.out.println(manager.undoLastTask().getDescription() + " was undone");
    }

    static void printEventSummary(Venue venue, GuestListManager guests, TaskManager tasks) {
        if (venue == null) {
            System.out.println("Select venue first");
            return;
        }
        if (guests.getAllGuests().isEmpty()) {
            System.out.println("Add guests first");
            return;
        }

        SeatingPlanner planner = new SeatingPlanner(venue);
        String chart = planner.generateSeating(guests.getAllGuests())
                .toString().replace("],", "]\n");

        System.out.println("Guests: " + guests.getAllGuests());
        System.out.println("Venue: " + venue.getName());
        System.out.println("Tasks remaining: " + tasks.remainingTaskCount());
        if (tasks.getUpcoming() != null) {
            System.out.println("Next task: " + tasks.getUpcoming().getDescription());
        }
        System.out.println("Seating Chart:\n" + chart);
    }
}
