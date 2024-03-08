package services;
import java.util.List;


import documents.User;
import documents.Workout;

public interface UserService {
    User createUser(String email, String password, String name, int age, double height,
                    double weight, String gender, String fitnessLevel,
                    String goals);

    User getUserByEmail(String email);

    User updateUser(String email, String password, String name, int age, double height,
                    double weight, String gender, String fitnessLevel,
                    String goals);

    void deleteUser(String email);

    List<User> getAllUsers();

	User addWorkout(String email, String workoutid);

	User deleteWorkout(String email, String workoutid);

	List<Workout> getWorkouts(String email);

	User updatePassword(String email, String passwordNative, String passwordUpdate);

	User updateLevel(String email, String level);

	User updateWeigth(String email, double weight);

	User updateEmail(String emailNative, String emailUpdate, String password);

	User updateHeight(String email, double heigth);

	User signUpUser(String email, String password, String name);

	User updateGoals(String email, String goals);

	User updateUserInfo(String email, int age, double height, double weight, String gender, String fitnessLevel,
			String goals);
}