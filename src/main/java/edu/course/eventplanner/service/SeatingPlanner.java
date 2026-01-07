package edu.course.eventplanner.service;

import edu.course.eventplanner.model.*;
import java.util.*;

public class SeatingPlanner {
    private final Venue venue;
    public SeatingPlanner(Venue venue) { this.venue = venue; }

    public static Map<String, List<Guest>> placeGuestsInTagGroup(List<Guest> guests){
        Map<String, List<Guest>> guestsWithTags = new HashMap<>(Map.of());
        //place every guest in their group
        for (Guest guest : guests) {
            String tag = guest.getGroupTag();
            if (!guestsWithTags.containsKey(tag)) {
                List<Guest> newTagGroup = new ArrayList<>();
                guestsWithTags.put(tag, newTagGroup);
            }
            guestsWithTags.get(tag).add(guest);
        }
        return guestsWithTags;
    }

    public Map<Integer, List<Guest>> generateSeating(List<Guest> guests) {
        TreeMap<Integer, List<Guest>> seatingLayout = new TreeMap<>();
        Map<String, List<Guest>> guestsWithTags = placeGuestsInTagGroup(guests);

        int tableNum = 0;
        int tableSize = venue.getSeatsPerTable();
        //place every guest at a table
        for(String tag: guestsWithTags.keySet()){
            List<Guest> tagGroup = guestsWithTags.get(tag);

            while(!tagGroup.isEmpty()){

                //if the remainder of the tag group is less than the table size
                if(tagGroup.size()<=tableSize){
                    tableSize = tagGroup.size();
                }
                //places table size number of guests in a table
                seatingLayout.put(tableNum, new ArrayList<>(tagGroup.subList(0, tableSize)));

                //removes those guests from the tag group
                tagGroup.subList(0, tableSize).clear();

                //if the table was filled
                if(seatingLayout.get(tableNum).size() == venue.getSeatsPerTable()){
                    tableNum++;
                    tableSize = venue.getSeatsPerTable();
                }
                else{
                    tableSize = venue.getSeatsPerTable()-seatingLayout.get(tableNum).size();
                }
            }
        }
        return seatingLayout;
    }
}
