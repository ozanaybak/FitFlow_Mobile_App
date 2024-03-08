package controlers;

import documents.Exercise;
import documents.ExerciseDTO;
import documents.User;
import documents.Workout;
import documents.WorkoutDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import services.ExerciseService;
import services.UserService;
import services.WorkoutService;
import repos.userRepo;
import repos.workoutRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private workoutRepo workoutRepo;
    
    @Autowired
    private userRepo userRepo;
    
    
    @Autowired
    private ExerciseService exerciseService;
    
    @Autowired
    private WorkoutService workoutService;

    @PostMapping("/signup")
    public ResponseEntity<User> signUpUser(
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String name) {
        User createdUser = userService.signUpUser(email, password, name);
        if (createdUser != null) {
            return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @PutMapping("/updateInfo")
    public ResponseEntity<User> updateUserInfo(
    		@RequestParam String email,
            @RequestParam int age,
            @RequestParam double height,
            @RequestParam double weight,
            @RequestParam String gender,
            @RequestParam String fitnessLevel,
            @RequestParam String goals) {
        User updatedUser = userService.updateUserInfo(email, age, height, weight, gender, fitnessLevel, goals);
        if (updatedUser != null) {
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @PutMapping("/updateGoals/{email}")
    public ResponseEntity<User> updateGoals(@PathVariable String email, @RequestParam String goals) {
        User updatedUser = userService.updateGoals(email, goals);
        if (updatedUser != null) {
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @PutMapping("/updateHeight/{email}")
    public ResponseEntity<User> updateHeight(@PathVariable String email, @RequestParam double height) {
        User updatedUser = userService.updateHeight(email, height);
        if (updatedUser != null) {
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/updateWeight/{email}")
    public ResponseEntity<User> updateWeight(@PathVariable String email, @RequestParam double weight) {
        User updatedUser = userService.updateWeigth(email, weight);
        if (updatedUser != null) {
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/updateLevel/{email}")
    public ResponseEntity<User> updateLevel(@PathVariable String email, @RequestParam String level) {
        User updatedUser = userService.updateLevel(email, level);
        if (updatedUser != null) {
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/updateEmail/{emailNative}")
    public ResponseEntity<User> updateEmail(@PathVariable String emailNative, @RequestParam String emailUpdate, @RequestParam String password) {
        User updatedUser = userService.updateEmail(emailNative, emailUpdate, password);
        if (updatedUser != null) {
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/updatePassword/{emailNative}")
    public ResponseEntity<User> updatePassword(@PathVariable String emailNative, @RequestParam String passwordNative, @RequestParam String passwordUpdate) {
        User updatedUser = userService.updatePassword(emailNative, passwordNative, passwordUpdate);
        if (updatedUser != null) {
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{email}")
    public ResponseEntity<Void> deleteUser(@PathVariable String email) {
        userService.deleteUser(email);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @PutMapping("/addworkout/{email}")
    public ResponseEntity<User> addWorkout(@PathVariable String email, @RequestParam String workoutId) {
        User updatedUser = userService.addWorkout(email, workoutId);
        System.out.println("hi");
        if (updatedUser != null) {
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{email}/workouts/{workoutId}")
    public ResponseEntity<User> deleteWorkout(@PathVariable String email, @PathVariable String workoutId) {
    	User updatedUser = userService.deleteWorkout(email, workoutId);
        if (updatedUser != null) {
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{email}/workoutsdto")
    public ResponseEntity<List<WorkoutDTO>> getWorkoutsDTO(@PathVariable String email) {
    	User user = userRepo.findByEmail(email);
        List<Workout> workouts = userService.getWorkouts(email);
        List<WorkoutDTO> workoutDTOs = new ArrayList<>();

        for (Workout wout : workouts) {
            WorkoutDTO workoutDTO = workoutService.returnDTO(wout.getId());
            if (workoutDTO != null) {
                workoutDTOs.add(workoutDTO);
            }
        }

        if (!workoutDTOs.isEmpty()) {
            return new ResponseEntity<>(workoutDTOs, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}
