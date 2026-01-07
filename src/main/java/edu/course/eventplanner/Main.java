package edu.course.eventplanner;

import edu.course.eventplanner.model.Venue;
import edu.course.eventplanner.service.GuestListManager;
import edu.course.eventplanner.service.VenueSelector;
import edu.course.eventplanner.util.Generators;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        GuestListManager guestListManager = new GuestListManager();
        VenueSelector venueSelector = new VenueSelector(Generators.generateVenues());

        //Get budget and guest count from user
        Scanner scanner = new Scanner(System.in);
        double budget;
        int guestCount;
        while (true) {
            try {
                System.out.print("Enter your budget: ");
                budget = scanner.nextDouble();

                System.out.print("Enter number of guests: ");
                guestCount = scanner.nextInt();

                break; // exit loop if both inputs are valid
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter numbers only.");
                scanner.nextLine(); // clear invalid input
            }
        }


    }
}
