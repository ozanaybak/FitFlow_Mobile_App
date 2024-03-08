package controlers;

import documents.Exercise;

import documents.ExerciseDTO;
import documents.User;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import services.ExerciseService;
import services.UserService;
import repos.userRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/exercises")
public class ExerciseController {

    @Autowired
    private ExerciseService exerciseService;
    
    @Autowired
    private userRepo userRepo;
    
    
    @PostMapping("/create")
    public ResponseEntity<Exercise> createExercise(
            @RequestParam String bodyPart,
            @RequestParam int sets,
            @RequestParam int reps,
            @RequestParam String level,
            @RequestParam String type,
            @RequestParam String name,
            @RequestParam String description) {

        Exercise createdExercise = exerciseService.createExercise(bodyPart, sets, reps, level, type, name, description);

        if (createdExercise != null) {
            return new ResponseEntity<>(createdExercise, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    
   
    @GetMapping("/dto")
    public ResponseEntity<List<ExerciseDTO>> getAllExercisesDTO() {
        List<Exercise> exercises = exerciseService.getAllExercises();
        
        if (exercises != null && !exercises.isEmpty()) {
            List<ExerciseDTO> exerciseDTOs = exercises.stream()
                    .map(exercise -> new ExerciseDTO(exercise.getId(), exercise.getName(), exercise.getBodyPart()))
                    .collect(Collectors.toList());
            
            return new ResponseEntity<>(exerciseDTOs, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }



    @GetMapping("/workout/{woutId}")
    public ResponseEntity<List<Exercise>> getAllExercisesWorkout(@PathVariable String woutId) {
        List<Exercise> exercises = exerciseService.getAllExercisesWorkout(woutId);
        if (exercises != null) {
            return new ResponseEntity<>(exercises, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    
}
