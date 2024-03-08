package documents;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import org.springframework.data.mongodb.core.mapping.DBRef;

@Document(collection = "users")
public class User {

    @Id
    private String id;

    private String email;
    private String password;
    private String name;
    private int age;
    private double height;
    private double weight;
    private String gender;
    private String fitnessLevel;
    private double bmi;  // Body Mass Index
    private String goals;
    
    @DBRef
    private List<Workout> workouts = new ArrayList<>();
    
    
    

    // Constructors, getters, setters, and additional methods...

    // Constructors
    public User() {
        // TODO Default constructor for Spring Data MongoDB
    }
    
    public User(String email, String password, String name) {
    	this.name = name;
    	this.email = email;
    	this.password = password;
    }

    public User(String email, String password, String name, int age, double height,
                double weight, String gender, String fitnessLevel,
                String goals) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.gender = gender;
        List<Workout> workouts = new ArrayList<>();
        this.workouts = workouts;
        this.fitnessLevel = fitnessLevel;
        this.goals = goals;
    }
    
    public void addInfo(int age, double height,
    		double weight, String gender,String fitnessLevel,
    		String goals) {
    	this.age = age;
        this.height = height;
        this.weight = weight;
        this.gender = gender;
        this.fitnessLevel = fitnessLevel;
        this.goals = goals;
        List<Workout> workouts = new ArrayList<>();
        this.workouts = workouts;
    }
    
    

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}


	public String getFitnessLevel() {
		return fitnessLevel;
	}

	public void setFitnessLevel(String fitnessLevel) {
		this.fitnessLevel = fitnessLevel;
	}

	public double getBmi() {
		return bmi;
	}

	public void setBmi() {
		double weight = this.weight;
		double height = this.height;
		this.bmi = weight/(height*height);
	}

	public String getGoals() {
		return goals;
	}

	public void setGoals(String goals) {
		this.goals = goals;
	}
    
	 @Override
	    public String toString() {
	        return "User{" +
	                "id='" + id + '\'' +
	                ", email='" + email + '\'' +
	                ", password='" + password + '\'' +
	                ", name='" + name + '\'' +
	                ", age=" + age +
	                ", height=" + height +
	                ", weight=" + weight +
	                ", gender='" + gender + '\'' +
	                ", fitnessLevel='" + fitnessLevel + '\'' +
	                ", bmi=" + bmi +
	                ", goals='" + goals + '\'' +
	                '}';
	    }
	 
	 public List<Workout> getWorkouts(){
		 return this.workouts;
	 }
	 
	 public void setWorkouts(List<Workout> workout){
		 this.workouts = workout;
	 }
}
    
    
    
    
    
    
    