package com.example.cs310frontend;


public class Exercises {
    private String title;
    private String bodyPart;
    private String id;

    private int reps;

    private int sets;

    private String description;

    private String level;
    private String type;

    private boolean selected; // Add this field

    // Add getter and setter for this field
    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public Exercises(String title, String bodyPart, String id) {
        this.title = title;
        this.bodyPart = bodyPart;
        this.id = id;
    }

    public Exercises(String title, String bodyPart, String id, int reps, int sets, String description, String level, String type) {
        this.title = title;
        this.bodyPart = bodyPart;
        this.id = id;
        this.reps = reps;
        this.sets = sets;
        this.description = description;
        this.level = level;
        this.type = type;
    }

    // Getters
    public String getTitle() { return title; }
    public String getBodyPart() { return bodyPart; }
    public String getId() { return id; }

    public int getReps() {
        return reps;
    }

    public int getSets() {
        return sets;
    }

    public String getDescription() {
        return description;
    }

    public String getLevel() {
        return level;
    }

    public String getType() {
        return type;
    }




}
