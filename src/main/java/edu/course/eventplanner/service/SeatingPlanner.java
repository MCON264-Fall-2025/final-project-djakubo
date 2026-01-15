package edu.course.eventplanner.service;

import edu.course.eventplanner.model.*;

import javax.swing.text.html.HTML;
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
    public static String determineMaxGroupTag(Map<String, List<Guest>> guestsWithTags){
        int maxSize = 0;
        String maxTag = "";
        for(String tag: guestsWithTags.keySet()){
            if(guestsWithTags.get(tag).size()>maxSize){
                maxSize = guestsWithTags.get(tag).size();
                maxTag = tag;
            }
        }
        return maxTag;
    }

    public Map<Integer, List<Guest>> generateSeating(List<Guest> guests) {
        TreeMap<Integer, List<Guest>> seatingLayout = new TreeMap<>();
        Map<String, List<Guest>> guestsWithTags = placeGuestsInTagGroup(guests);

        int tableNum = 0;
        int tableSize = venue.getSeatsPerTable();

        while(!guestsWithTags.isEmpty()) {

            //get tag group with the most guests
            String maxGroupTag = determineMaxGroupTag(guestsWithTags);
            List<Guest> tagGroup = guestsWithTags.get(maxGroupTag);

            //Initiate table number
            if (!seatingLayout.containsKey(tableNum)) {
                seatingLayout.put(tableNum, new ArrayList<>());
            }
            //if all tables have been filled, find the table with the most remaining seats
            else{
                for (int i = 0; i < venue.getTables(); i++) {
                    if (seatingLayout.get(i).size() < venue.getSeatsPerTable()
                            && seatingLayout.get(i).size() > seatingLayout.get(tableNum).size()) {
                        tableNum = i;
                    }
                }
                tableSize = venue.getSeatsPerTable() - seatingLayout.get(tableNum).size();
            }

            //if the size of the tag group is less than the table size
            if (tagGroup.size() <= tableSize) {
                tableSize = tagGroup.size();
            }

            //place table size number of guests in a table
            seatingLayout.get(tableNum).addAll(tagGroup.subList(0, tableSize));

            //removes those guests from the tag group
            tagGroup.subList(0, tableSize).clear();

            if (tagGroup.isEmpty()) {guestsWithTags.remove(maxGroupTag);}

            if(tableNum < venue.getTables()-1){
                tableNum++;
                tableSize = venue.getSeatsPerTable();
            }
            else{tableNum = 0;}
        }
        return seatingLayout;
    }
}
