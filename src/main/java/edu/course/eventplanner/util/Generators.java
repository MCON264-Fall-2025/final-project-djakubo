package edu.course.eventplanner.util;

import edu.course.eventplanner.model.*;
import java.util.*;

public class Generators {
    public static List<Venue> generateVenues() {
        return List.of(
            new Venue("Atrium Ballroom",20000,800,100,8),
            new Venue("Tiferes Elka",10000,750,75,10),
            new Venue("Ateres Charna",7000,600,60,10),
            new Venue("Valley Terrace",6000,400,50,8)
        );
    }
    public static List<Guest> GenerateGuests(int n) {
        List<Guest> guests = new ArrayList<>();
        String[] groups = {"family","friends","neighbors","coworkers"};
        for(int i=1;i<=n;i++){
            guests.add(new Guest("Guest"+i, groups[i%groups.length]));
        }
        return guests;
    }
}
