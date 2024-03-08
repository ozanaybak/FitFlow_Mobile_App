package com.example.cs310frontend;

import java.util.List;

public class Workouts {
    private String title;
    private String id;
    private String creator;


    //For the detailed view
    private String description;
    private String reps;
    private String calsBurned;

    private String level;

    public Workouts(String title, String creator, String id) {
        this.title = title;
        this.creator = creator;
        this.id = id;
    }

    public Workouts(String title, String id, String creator, String description, String calsBurned, String level) {
        this.title = title;
        this.id = id;
        this.creator = creator;
        this.description = description;
        this.calsBurned = calsBurned;
        this.level = level;
    }



    // Getters
    public String getTitle() { return title; }
    public String getId() {return id; }
    public String getCreator() { return creator; }

    public String getLevel() {
        return level;
    }

    public String getDescription() {
        return description;
    }
}
