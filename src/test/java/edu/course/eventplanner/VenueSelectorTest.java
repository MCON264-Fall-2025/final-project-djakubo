package edu.course.eventplanner;

import edu.course.eventplanner.model.Venue;
import edu.course.eventplanner.service.VenueSelector;
import edu.course.eventplanner.util.Generators;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class VenueSelectorTest {
    List<Venue> venues = Generators.generateVenues();
    VenueSelector venueSelector = new VenueSelector(venues);
    int guestCount;
    int budget;


    @Test
    void testSelectOnlyOptionBasedOnBudget(){
        budget = 6000;
        guestCount = 200;
        assertEquals(venues.get(3).getName(), venueSelector.selectVenue(budget, guestCount).getName());
    }

    @Test
    void testSelectOnlyOptionBasedOnGuestCount(){
        budget = 40000;
        guestCount = 800;
        assertEquals(venues.get(0).getName(), venueSelector.selectVenue(budget, guestCount).getName());
    }

    @Test
    void testSelectCheapestOption(){
        budget = 30000;
        guestCount = 100;
        assertEquals(venues.get(3).getName(), venueSelector.selectVenue(budget, guestCount).getName());
    }

    @Test
    void testReturnsNullBasedOnBudget(){
        budget = 5000;
        guestCount = 100;
        assertNull(venueSelector.selectVenue(budget, guestCount));
    }

    @Test
    void testReturnsNullBasedOnGuestCount(){
        budget = 40000;
        guestCount = 1000;
        assertNull(venueSelector.selectVenue(budget, guestCount));
    }

    @Test
    void testReturnsNullBasedOnCombo(){
        budget = 15000;
        guestCount = 770;
        assertNull(venueSelector.selectVenue(budget, guestCount));
    }

}
