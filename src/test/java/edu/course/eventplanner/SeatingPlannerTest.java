package edu.course.eventplanner;

import edu.course.eventplanner.model.Guest;
import edu.course.eventplanner.model.Venue;
import edu.course.eventplanner.service.SeatingPlanner;
import edu.course.eventplanner.service.VenueSelector;
import edu.course.eventplanner.util.Generators;
import org.junit.jupiter.api.Test;

import java.security.PrivateKey;
import java.util.List;

public class SeatingPlannerTest {

    private VenueSelector venueSelector = new VenueSelector(Generators.generateVenues());
    private List<Guest> guests = Generators.GenerateGuests(10);
    private Venue venue = new Venue("Tiferes Genendle",1000,10,5,2);


    @Test
    void testSeatingPlanner(){
        SeatingPlanner seatingPlanner = new SeatingPlanner(venue);
        System.out.println(seatingPlanner.generateSeating(guests).toString());
    }
}
