package services;

import documents.Exercise;
import documents.User;
import documents.Workout;
import documents.WorkoutDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repos.userRepo;
import repos.workoutRepo;
import repos.exerciseRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class WorkoutServiceImpl implements WorkoutService {

    @Autowired
    private workoutRepo workoutRepository;

    @Autowired
    private userRepo userRepo;
    
    @Autowired 
    private exerciseRepo exerciseRepo;

    @Override
    public Workout createWorkout(String name, String email, String level, double calsBurned, String description) {
    	User publisher = userRepo.findByEmail(email);
        Workout workout = new Workout(name, publisher, level, null, calsBurned, description);
        workoutRepository.save(workout);
        return workout;
    }

    @Override
    public Workout addExercises(String workoutId, List<Exercise> exercises) {
        Optional<Workout> optionalWorkout = workoutRepository.findById(workoutId);
        if (optionalWorkout.isPresent()) {
            Workout workout = optionalWorkout.get();
            workout.setExercises(exercises);
            return workoutRepository.save(workout);
        }
        return null;
    }

    
    @Override
    public Workout addExercise(String workoutId, String exerciseId) {
        Optional<Workout> optionalWorkout = workoutRepository.findById(workoutId);
        Optional<Exercise> optionalExercise = exerciseRepo.findById(exerciseId);

        if (optionalWorkout.isPresent() && optionalExercise.isPresent()) {
            Workout workout = optionalWorkout.get();
            Exercise exercise = optionalExercise.get();
            if (workout.getExercises() == null) {
                workout.setExercises(new ArrayList<>()); // Ensure exercises list is initialized
            }
            workout.getExercises().add(exercise);

            return workoutRepository.save(workout);
        }

        return null;
    }

    @Override
    public Workout getWorkoutById(String workoutId) {
        Optional<Workout> optionalWorkout = workoutRepository.findById(workoutId);
        return optionalWorkout.orElse(null);
    }

    @Override
    public List<Workout> getAllWorkouts() {
        return workoutRepository.findAll();
    }

    @Override
    public List<Exercise> getExercises(String workoutId) {
        Optional<Workout> optionalWorkout = workoutRepository.findById(workoutId);
        return optionalWorkout.map(Workout::getExercises).orElse(null);
    }

    @Override
    public void deleteWorkout(String email, String id) {
        User publisher = userRepo.findByEmail(email);
        if (publisher != null) {
            Optional<Workout> optionalWorkout = workoutRepository.findById(id);
            if (optionalWorkout.isPresent()) {
                Workout workout = optionalWorkout.get();
                if (workout.getPublisher().getEmail().equals(email)){
                    workoutRepository.deleteById(id);
                }
            }
        }
    }
    
    @Override
    public WorkoutDTO returnDTO(String workoutId) {
        Optional<Workout> optionalWorkout = workoutRepository.findById(workoutId);
        if (optionalWorkout.isPresent()) {
            Workout workout = optionalWorkout.get();
            WorkoutDTO woutDto = new WorkoutDTO(workout.getId(), workout.getName(), workout.getPublisher().getName());
            return woutDto;
        }
        return null;
    }




}
