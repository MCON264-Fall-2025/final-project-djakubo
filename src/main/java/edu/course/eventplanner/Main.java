package edu.course.eventplanner;

import edu.course.eventplanner.model.Guest;
import edu.course.eventplanner.model.Task;
import edu.course.eventplanner.model.Venue;
import edu.course.eventplanner.service.GuestListManager;
import edu.course.eventplanner.service.SeatingPlanner;
import edu.course.eventplanner.service.TaskManager;
import edu.course.eventplanner.service.VenueSelector;
import edu.course.eventplanner.util.Generators;

import javax.swing.*;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        GuestListManager guestListManager = new GuestListManager();
        TaskManager taskManager = new TaskManager();
        SeatingPlanner seatingPlanner;
        VenueSelector venueSelector = new VenueSelector(Generators.generateVenues());
        Venue venue = null;
        List<Guest> guests;


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
            String input = JOptionPane.showInputDialog(
                    null,
                    menu,
                    "Event Planner Menu",
                    JOptionPane.PLAIN_MESSAGE
            );

            if (input == null) {
                // User hit cancel → treat as exit
                break;
            }

            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Please enter a number.");
                continue;
            }

            switch (choice) {
                case 1:
                    guests = Generators.GenerateGuests(100);
                    venue = venueSelector.selectVenue(10000, 100);
                    seatingPlanner = new SeatingPlanner(venue);
                    String seatingChart = seatingPlanner.generateSeating(guests).toString().replace("],", "]\n");
                    JOptionPane.showMessageDialog(null, "Budget: $10,000\nGuestCount: 100\nVenue: " +
                            venue.getName() + "\nSeating Chart: \n" + seatingChart);
                    break;

                case 2:
                    String name = JOptionPane.showInputDialog(null, "Enter guest name:");
                    if (name == null) {
                        JOptionPane.showMessageDialog(null, "Guest name cannot be empty.");
                        break;
                    }
                    String groupTag = JOptionPane.showInputDialog(null, "Enter guest group tag:");
                    if (groupTag == null) {
                        JOptionPane.showMessageDialog(null, "Group tag cannot be empty.");
                        break;
                    }

                    Guest guest = new Guest(name, groupTag);
                    guestListManager.addGuest(guest);
                    JOptionPane.showMessageDialog(null, guest.getName() + " has been added to the guest list");
                    break;

                case 3:
                    name = JOptionPane.showInputDialog(null, "Enter guest name to remove:");
                    if(!guestListManager.removeGuest(name)){
                        JOptionPane.showMessageDialog(null, "No such guest found");
                        break;
                    }
                    JOptionPane.showMessageDialog(null, name + " was removed" );
                    break;

                case 4:
                    //Get budget and guest count from user
                    double budget = 0;
                    int guestCount = 0;

                    //find valid venue
                    while (true) {
                        try {
                            String budgetInput = JOptionPane.showInputDialog("Enter your budget: ");
                            if (budgetInput == null) return;
                            String guestInput = JOptionPane.showInputDialog("Enter number of guests: ");
                            if (guestInput == null) return;

                            budget = Double.parseDouble(budgetInput);
                            guestCount = Integer.parseInt(guestInput);

                            if (venueSelector.selectVenue(budget, guestCount) == null) {
                                continue;
                            }
                            break;
                        } catch (NumberFormatException e) {
                            JOptionPane.showMessageDialog(null, "Invalid input. Please enter numbers only.");
                        }
                    }

                    venue = venueSelector.selectVenue(budget, guestCount);
                    JOptionPane.showMessageDialog(null, "Venue: " + venue.getName());
                    break;

                case 5:
                    if (venue == null) {
                        JOptionPane.showMessageDialog(null, "Select venue first");
                        break;
                    }
                    if(guestListManager.getAllGuests().isEmpty()){
                        JOptionPane.showMessageDialog(null, "Add guests first");
                        break;
                    }
                    seatingPlanner = new SeatingPlanner(venue);
                    String chart = seatingPlanner.generateSeating(guestListManager.getAllGuests()).toString().replace("],", "]\n");
                    JOptionPane.showMessageDialog(null,  "Seating Chart: \n" + chart);
                    break;

                case 6:
                    String taskInput = JOptionPane.showInputDialog("Enter task");
                    Task task = new Task(taskInput);
                    taskManager.addTask(task);
                    JOptionPane.showMessageDialog(null, task.getDescription() + " has been added to the task list");
                    break;

                case 7:
                    if(taskManager.remainingTaskCount()==0){
                        JOptionPane.showMessageDialog(null, "No tasks to be completed");
                        break;
                    }
                    taskManager.executeNextTask();
                    JOptionPane.showMessageDialog(null, taskManager.getCompleted().getDescription() + " was executed");
                    break;

                case 8:
                    if(taskManager.getCompleted() == null){
                        JOptionPane.showMessageDialog(null, "No task was completed");
                        break;
                    }
                    JOptionPane.showMessageDialog(null, taskManager.undoLastTask().getDescription() + " was undone");
                    break;

                case 9:
                    if (venue == null) {
                        JOptionPane.showMessageDialog(null, "Select venue first");
                        break;
                    }
                    if(guestListManager.getAllGuests().isEmpty()){
                        JOptionPane.showMessageDialog(null, "Add guests first");
                        break;
                    }
                    seatingPlanner = new SeatingPlanner(venue);
                    String sChart = seatingPlanner.generateSeating(guestListManager.getAllGuests()).toString().replace("],", "]\n");
                    JOptionPane.showMessageDialog(null, "Guests: " + guestListManager.getAllGuests().toString()+"\nVenue: " + venue.getName()
                            + "\nTasks remaining: " +taskManager.remainingTaskCount()+ "\nNext task: " + taskManager.getUpcoming().getDescription() +
                            "\nSeating Chart: \n" + sChart);
                    break;

                case 0:
                    JOptionPane.showMessageDialog(null, "Goodbye!");
                    break;

                default:
                    JOptionPane.showMessageDialog(null, "Invalid choice.");
            }
        }
        System.exit(0);
    }

}