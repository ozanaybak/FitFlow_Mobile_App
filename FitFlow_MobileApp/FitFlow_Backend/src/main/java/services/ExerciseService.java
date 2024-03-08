package services;

import documents.Exercise;
import documents.ExerciseDTO;
import documents.User;

import java.util.List;

public interface ExerciseService {
    Exercise createExercise(String bodyPart, int sets, int reps, String level, String type, String name, String description);

    List<Exercise> getAllExercises();
    
    List<Exercise> getAllExercisesWorkout(String idWorkout);
    
    ExerciseDTO returnDTO(String exerciseId);
}
