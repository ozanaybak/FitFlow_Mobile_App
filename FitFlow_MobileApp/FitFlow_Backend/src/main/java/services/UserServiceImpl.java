package services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import documents.User;
import documents.Workout;
import repos.userRepo;
import repos.workoutRepo;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private userRepo userRepo;  // Assuming you have a UserRepository for database operations
    @Autowired
    private workoutRepo workoutRepo;
    
    
    @Override
    public User signUpUser(String email, String password, String name) {
    	User checkUserMail = userRepo.findByEmail(email);
    	User usr1 = null;
    	if(checkUserMail == null) {
    		usr1 = new User(email, password, name);
    		userRepo.save(usr1);
    	}

    	return usr1;
    }
    
    
    @Override
    public User createUser(String email, String password, String name, int age, double height,
                           double weight, String gender, String fitnessLevel, 
                           String goals) {
    	
    	
    	User checkUserMail = userRepo.findByEmail(email);
    	User usr1 = null;
    	if(checkUserMail == null) {
	    	usr1 = new User(email, password, name, age, height, weight, gender, fitnessLevel, goals);
	    	usr1.setBmi();
	    	userRepo.save(usr1);
    	}
    	return usr1;
    }
    
    @Override
    public User updateUserInfo(String email, int age, double height, double weight, String gender, String fitnessLevel, String goals) {
        User user = userRepo.findByEmail(email);

        if (user != null) {
            user.addInfo(age, height, weight, gender, fitnessLevel, goals);
            userRepo.save(user);
        }

        return user;
    }


	@Override
	public User getUserByEmail(String email) {
		User usr1 = userRepo.findByEmail(email);
		return usr1;
	}

	@Override
	public User updateHeight(String email, double heigth){
		User usr1 = userRepo.findByEmail(email);
		usr1.setHeight(heigth);
		userRepo.save(usr1);
		return usr1;
	}

	
	@Override
	public User updateWeigth(String email, double weight){
		User usr1 = userRepo.findByEmail(email);
		usr1.setWeight(weight);
		return userRepo.save(usr1);
	}
	
	@Override
	public User updateLevel(String email, String level){
		User usr1 = userRepo.findByEmail(email);
		usr1.setFitnessLevel(level);
		userRepo.save(usr1);
		return usr1;
	}
	
	@Override
	public User updateEmail(String emailNative, String emailUpdate, String password){
		User usr1 = userRepo.findByEmail(emailNative);
		if(usr1.getPassword().equals(password)) {
			usr1.setEmail(emailUpdate);
			userRepo.save(usr1);
		}
		return usr1;
	}
	
	@Override
	public User updatePassword(String email, String passwordNative, String passwordUpdate) {
	    User usr1 = userRepo.findByEmail(email);
	    if (usr1.getPassword().equals(passwordNative)) {
	        usr1.setPassword(passwordUpdate);
	        userRepo.save(usr1);
	    }
	    return usr1;
	}


	@Override
	public void deleteUser(String email) {
		userRepo.deleteByEmail(email);
	}
	
	@Override
	public User updateGoals(String email, String goals){
		User usr1 = userRepo.findByEmail(email);
		usr1.setGoals(goals);
		userRepo.save(usr1);
		return usr1;
	}

	@Override
	public List<User> getAllUsers() {
		List<User> users = userRepo.findAll();
		return users;
	}
	
	@Override
    public User addWorkout(String email, String workoutid) {
        User user = userRepo.findByEmail(email);
        Optional<Workout> optionalWorkout = workoutRepo.findById(workoutid);
        Workout workout = optionalWorkout.get();
        
        if (user != null) {
        	if(workout.getPublisher().getEmail().equals(user.getEmail())) {
	            List<Workout> workouts = user.getWorkouts();
	            workouts.add(workout);
	            user.setWorkouts(workouts);
	            userRepo.save(user);
        	}
        	else {
        		return null;
        	}
        }

        return user;
    }
	
	@Override
	public User deleteWorkout(String email, String workoutid) {
		User user = userRepo.findByEmail(email);
		Optional<Workout> optionalWorkout = workoutRepo.findById(workoutid);
        Workout workout = optionalWorkout.get();
		if (user != null) {
            List<Workout> workouts = user.getWorkouts();
            workouts.removeIf(w -> w.getId().equals(workout.getId()));
            user.setWorkouts(workouts);
            userRepo.save(user);
        }
        return user;
	}

	@Override
	public User updateUser(String email, String password, String name, int age, double height, double weight,
			String gender, String fitnessLevel, String goals) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override 
	public List<Workout> getWorkouts(String email){
		User user = userRepo.findByEmail(email);
		return user.getWorkouts();
	}
	
    // Other methods overridden from UserService interface
}