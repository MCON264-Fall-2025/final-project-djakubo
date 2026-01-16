package edu.course.eventplanner.service;

import edu.course.eventplanner.model.Guest;

import javax.swing.*;
import java.util.*;

public class GuestListManager {
    final LinkedList<Guest> guests = new LinkedList<>();
    final Map<String, Guest> guestByName = new HashMap<>();


    public void addGuest(Guest guest) {
        guests.add(guest);
        guestByName.put(guest.getName(), guest);
    }

    public boolean removeGuest(String guestName) {
        if(guests.contains(guestByName.get(guestName))){
            guests.remove(guestByName.get(guestName));
            guestByName.remove(guestName);
            return true;
        }
        else{
            return false;
        }
    }
    public Guest findGuest(String guestName) {
        if(guestByName.containsKey(guestName)){
            return guestByName.get(guestName);
        }
        else{
            return null;
        }
    }
    public int getGuestCount() { return guests.size(); }
    public List<Guest> getAllGuests() { return guests; }
}
