package services;

import documents.Exercise;
import documents.User;
import documents.WorkoutDTO;
import documents.Workout;

import java.util.List;

public interface WorkoutService {
    Workout createWorkout(String name, String email, String level, double calsBurned, String description);
    
    Workout addExercises(String workoutId, List<Exercise> exercises);
    
    Workout addExercise(String workoutId, String exerciseId);

    Workout getWorkoutById(String workoutId);

    List<Workout> getAllWorkouts();
    
    List<Exercise> getExercises(String workoutId);
    
    WorkoutDTO returnDTO(String workoutId);

    void deleteWorkout(String email, String id);
}
