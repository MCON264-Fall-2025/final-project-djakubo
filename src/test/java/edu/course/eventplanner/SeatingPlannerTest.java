package edu.course.eventplanner;

import edu.course.eventplanner.model.Guest;
import edu.course.eventplanner.model.Venue;
import edu.course.eventplanner.service.SeatingPlanner;
import edu.course.eventplanner.service.VenueSelector;
import edu.course.eventplanner.util.Generators;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class SeatingPlannerTest {
    private List<Guest >guests = Generators.GenerateGuests(10);
    private Venue venue;

    @Test
    void testTwoSeats(){
        venue = new Venue("Tiferes Genendle",1000,10,5,2);
        SeatingPlanner seatingPlanner = new SeatingPlanner(venue);
        assertNull(seatingPlanner.generateSeating(guests).get(5));//should only be five tables
        assertEquals("[Guest2 (neighbors), Guest6 (neighbors)]", seatingPlanner.generateSeating(guests).get(0).toString());
    }

    @Test
    void testThreeSeats(){
        venue = new Venue("Tiferes Genendle",1000,10,5,3);
        SeatingPlanner seatingPlanner = new SeatingPlanner(venue);
        assertNull(seatingPlanner.generateSeating(guests).get(4));//should only be four tables
        assertEquals("[Guest2 (neighbors), Guest6 (neighbors), Guest10 (neighbors)]", seatingPlanner.generateSeating(guests).get(0).toString());
    }

    @Test
    void testFourSeats(){
        venue = new Venue("Tiferes Genendle",1000,10,5,4);
        SeatingPlanner seatingPlanner = new SeatingPlanner(venue);
        //System.out.println(seatingPlanner.generateSeating(guests).toString());
        assertNull(seatingPlanner.generateSeating(guests).get(4));//should still be four tables
        assertEquals("[Guest2 (neighbors), Guest6 (neighbors), Guest10 (neighbors)]", seatingPlanner.generateSeating(guests).get(0).toString());
    }

}
