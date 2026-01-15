package edu.course.eventplanner;

import edu.course.eventplanner.model.Guest;
import edu.course.eventplanner.service.GuestListManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import static org.junit.jupiter.api.Assertions.*;

public class GuestListManagerTest {

    GuestListManager guestList = new GuestListManager();
    Guest newGuest = new Guest("Laycee", "Friends");

    @BeforeEach
    void add_guest(){guestList.addGuest(newGuest);
    }
    @Test
    void test_add_guest(){
        assertEquals(new LinkedList<>(List.of(newGuest)), guestList.getAllGuests());
    }

    @Test
    void test_remove_existing_guest(){
        assertTrue(guestList.removeGuest("Laycee"));
    }
    @Test
    void test_remove_nonexisting_guest(){
        assertFalse(guestList.removeGuest("Bruchele"));
    }
    @Test
    void test_find_existing_guest(){
        Guest foundGuest = guestList.findGuest("Laycee");
        assertEquals(newGuest, foundGuest);
    }
    @Test
    void test_find_nonexisting_guest(){
        assertNull(guestList.findGuest("Bruchele"));
    }

    @Test
    void test_get_guest_count(){
        assertEquals(1, guestList.getGuestCount());
    }

    @Test
    void test_get_all_guests(){
        Guest newGuest2 = new Guest("Rikki", "Friends");
        guestList.addGuest(newGuest2);
        assertEquals(new LinkedList<>(List.of(newGuest, newGuest2)), guestList.getAllGuests());
    }

}
