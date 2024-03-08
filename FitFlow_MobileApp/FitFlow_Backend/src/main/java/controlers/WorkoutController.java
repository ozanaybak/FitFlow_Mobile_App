package controlers;

import documents.Exercise;
import documents.ExerciseDTO;
import documents.Workout;
import documents.WorkoutDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import services.ExerciseService;
import services.WorkoutService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.function.Function;


@RestController
@RequestMapping("/api/workouts")
public class WorkoutController {

    @Autowired
    private WorkoutService workoutService;
    
    @Autowired
    private ExerciseService exerciseService;

    @PostMapping("/create")
    public ResponseEntity<Workout> createWorkout(@RequestParam String name, @RequestParam String email,
                                                @RequestParam String level, @RequestParam double calsBurned,
                                                @RequestParam String description) {
    	
        Workout createdWorkout = workoutService.createWorkout(name, email, level, calsBurned, description);
        if (createdWorkout != null) {
            return new ResponseEntity<>(createdWorkout, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Workout> getWorkoutById(@PathVariable String id) {
        Workout workout = workoutService.getWorkoutById(id);
        if (workout != null) {
            return new ResponseEntity<>(workout, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/alldto")
    public ResponseEntity<List<WorkoutDTO>> getAllWorkoutsDTO() {
        List<Workout> workouts = workoutService.getAllWorkouts();
        if (workouts != null && !workouts.isEmpty()) {
            List<WorkoutDTO> workoutDTOs = workouts.stream()
                    .map(workout -> workoutService.returnDTO(workout.getId()))
                    .collect(Collectors.toList());

            return new ResponseEntity<>(workoutDTOs, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping("/exercisesdto/{id}")
    public ResponseEntity<List<ExerciseDTO>> getExercisesDTO(@PathVariable String id) {
        List<Exercise> exercises = workoutService.getExercises(id);
        List<ExerciseDTO> exerciseDTOs = new ArrayList<>();

        for (Exercise exercise : exercises) {
            ExerciseDTO exerciseDTO = exerciseService.returnDTO(exercise.getId());
            if (exerciseDTO != null) {
                exerciseDTOs.add(exerciseDTO);
            }
        }

        if (!exerciseDTOs.isEmpty()) {
            return new ResponseEntity<>(exerciseDTOs, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @PutMapping("/addExercise/{workoutId}")
    public ResponseEntity<Workout> addExerciseToWorkout(
            @PathVariable String workoutId,
            @RequestParam String exerciseId) {
        
        Workout updatedWorkout = workoutService.addExercise(workoutId, exerciseId);
        
        if (updatedWorkout != null) {
            return new ResponseEntity<>(updatedWorkout, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{email}")
    public ResponseEntity<Void> deleteWorkout(@PathVariable String email, @RequestParam String id) {
        workoutService.deleteWorkout(email, id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/dto/{id}")
    public ResponseEntity<WorkoutDTO> getWorkoutDTO(@PathVariable String id) {
        WorkoutDTO workoutDTO = workoutService.returnDTO(id);
        if (workoutDTO != null) {
            return new ResponseEntity<>(workoutDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
