package edu.course.eventplanner.service;

import edu.course.eventplanner.model.Task;
import java.util.*;

public class TaskManager {
    private final Queue<Task> upcoming = new LinkedList<>();
    private final Stack<Task> completed = new Stack<>();

    public Task getUpcoming(){
        if(upcoming.isEmpty()){
            return null;
        }
        return upcoming.peek();
    }
    public Task getCompleted(){
        if(completed.isEmpty()){
            return null;
        }
        return completed.peek();
    }
    public void addTask(Task task) {
        upcoming.add(task);
    }
    public Task executeNextTask() {
        if(upcoming.isEmpty()){
            return null;
        }
        completed.push(upcoming.peek());
        return upcoming.poll();
    }
    public Task undoLastTask() {
        if(completed.isEmpty()){
            return null;
        }
        upcoming.add(completed.peek());
        return completed.pop();
    }
    public int remainingTaskCount() {
        return upcoming.size();
    }
}
