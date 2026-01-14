package edu.course.eventplanner.service;

import edu.course.eventplanner.model.Guest;
import edu.course.eventplanner.model.Venue;

import java.util.*;

public class SeatingPlannerCopy {
    private final Venue venue;

    public SeatingPlannerCopy(Venue venue) {
        this.venue = venue;
    }

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

                if(!seatingLayout.containsKey(tableNum)){
                    seatingLayout.put(tableNum, new ArrayList<>());
                }
                //if the size of the tag group is less than the table size
                if(tagGroup.size()<=tableSize){
                    tableSize = tagGroup.size();
                }

                //places table size number of guests in a table
                seatingLayout.get(tableNum).addAll(tagGroup.subList(0, tableSize));

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
