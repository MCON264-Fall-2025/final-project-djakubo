package edu.course.eventplanner.service;

import edu.course.eventplanner.model.Venue;

import java.util.List;
import java.util.TreeMap;

public class VenueSelector {
    private final List<Venue> venues;
    private final TreeMap<Double, Integer> sortedVenues = new TreeMap<>();

    public VenueSelector(List<Venue> venues) {
        this.venues = venues;
    }

    public Venue selectVenue(double budget, int guestCount) {

        // if empty list
        if (venues.isEmpty()) {
            return null;
        }

        // sort venues
        sortedVenues.clear();
        for (int i = 0; i < venues.size(); i++) {
            sortedVenues.put(venues.get(i).getCost(), i);
        }

        // if the budget is below the cost of the cheapest venue
        if (budget < sortedVenues.firstKey()) {
            System.out.println("No venue found for this budget");
            return null;
        }

        // variable to keep track if there is another possible capacity at a different budget
        boolean guestCountFound = false;

        for (Double cost : sortedVenues.keySet()) {
            // if the budget is below cost and the guest count is below capacity
            if (budget >= cost && guestCount <= venues.get(sortedVenues.get(cost)).getCapacity()) {
                return venues.get(sortedVenues.get(cost));
            } else if (guestCount <= venues.get(sortedVenues.get(cost)).getCapacity()) {
                guestCountFound = true;
            }
        }

        // if no venue was found
        if (!guestCountFound) {
            System.out.println("No venue found for this capacity");
        } else {
            System.out.println("No capacity found for this guest count at this budget");
        }

        return null;
    }
}
