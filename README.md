# Event Planner Mini
This project demonstrates practical use of data structures:
linked lists, stacks, queues, maps, trees, sorting, and searching.
This is an event planner that supports various different functions for users who are planning an event. 
Users can create a guest list, create tasks to be completed, select a venue based on budget and guest count, and generate a seating chart based on decided groups.
The project focuses on keeping the code organized while solving realistic planning problems.
It shows how data structures can be used in a practical and easy-to-understand way.

- I used a linked list to contain all the guests.
- I used a stack to hold completed tasks for easy undo operations.
- I used a queue to hold upcoming tasks because tasks should be first in first out.
- I used a hashmap to place each guest in its tag group that was used to help generate the seating planner.
- I also used a hashmap to place each guest to their name for faster guest retrieval. 
- I used a treemap to hold the table numbers with their guests because the seating planner should list the table numbers in order. 
- I also used a treemap to place the venues in order of their cost for faster venue selection.

# Big O Complexity
- Finding a guest – O(1)  
  Uses a HashMap lookup which is constant time.

- Selecting a venue – O(n log n)  
  Venues are inserted into a sorted map, which requires logarithmic time per insertion, followed by a linear scan.

- Generating seating – O(n²)  
  Guests are placed incrementally, and on each iteration the algorithm scans all remaining groups to find the largest one.
  Because this repeated scanning happens up to n times as guests are seated, the overall runtime grows quadratically.




