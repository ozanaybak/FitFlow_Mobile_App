package services;

import documents.Exercise;
import documents.ExerciseDTO;
import documents.User;
import documents.Workout;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repos.exerciseRepo;
import repos.userRepo;
import repos.workoutRepo;

import java.util.List;
import java.util.Optional;

@Service
public class ExerciseServiceImpl implements ExerciseService {

    @Autowired
    private exerciseRepo exerciseRepo;

    @Autowired
    private userRepo userRepo;
    
    @Autowired
    private workoutRepo workoutRepo;

    @Override
    public Exercise createExercise(String bodyPart, int sets, int reps, String level, String type, String name, String description) {  
    	Exercise ex = new Exercise(bodyPart, sets, reps, level, type, name, description);
    	exerciseRepo.save(ex);
        return ex;
    }


    @Override
    public List<Exercise> getAllExercises() {
        return exerciseRepo.findAll();
    }
    
    @Override 
    public List<Exercise> getAllExercisesWorkout(String woutId){
    	Optional<Workout> work1 = workoutRepo.findById(woutId);
    	if(work1.isPresent()) {
    		Workout workout = work1.get();
    		List<Exercise> exercises = workout.getExercises();
    		return exercises;
    	}
    	return null;
    }

    
    @Override
    public ExerciseDTO returnDTO(String exerciseId) {
        Optional<Exercise> optionalExercise = exerciseRepo.findById(exerciseId);

        if (optionalExercise.isPresent()) {
            Exercise exercise = optionalExercise.get();
            ExerciseDTO exerciseDTO = new ExerciseDTO(exerciseId, exercise.getName(), exercise.getBodyPart());

            return exerciseDTO;
        } else {
            return null;
        }
    }
}
