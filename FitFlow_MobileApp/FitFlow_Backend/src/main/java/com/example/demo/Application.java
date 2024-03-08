package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import documents.Exercise;
import documents.User;
import documents.Workout;
import repos.exerciseRepo;
import repos.userRepo;
import repos.workoutRepo;
import services.ExerciseService;
import services.ExerciseServiceImpl;
import services.UserService;
import services.UserServiceImpl;
import services.WorkoutService;
import services.WorkoutServiceImpl;

import java.util.ArrayList;
import java.util.List;


@SpringBootApplication
@EnableMongoRepositories(basePackages = {"repos"})
@ComponentScan(basePackages = {"com.example.demo", "services", "controlers"})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}

