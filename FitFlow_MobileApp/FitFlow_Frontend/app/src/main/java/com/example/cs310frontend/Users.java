package com.example.cs310frontend;

public class Users {
    private String name;
    private int age;
    private double weight;
    private double height;
    private String goals;
    private String fitnessLevel;

    public Users(String name, int age, double weight, double height, String goals, String fitnessLevel) {
        this.name = name;
        this.age = age;
        this.weight = weight;
        this.height = height;
        this.goals = goals;
        this.fitnessLevel = fitnessLevel;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public double getWeight() {
        return weight;
    }

    public double getHeight() {
        return height;
    }

    public String getGoals() {
        return goals;
    }

    public String getFitnessLevel() {
        return fitnessLevel;
    }
}