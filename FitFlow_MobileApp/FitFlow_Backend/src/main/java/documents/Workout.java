package documents;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "workouts")
public class Workout {

    @Id
    private String id;

    private String name;
    private String level;
    private List<Exercise> exercises = new ArrayList<>(); // List of exercises in the workout
    private double calsBurned; // Calories burned during the workout
    private String description;

    @DBRef
    private User publisher;
    
    // Constructors, getters, setters, and additional methods...

    // Constructors
    public Workout() {
        // TODO Default constructor for Spring Data MongoDB
    }

    public Workout(String name, User publisher, String level, List<Exercise> exercises, double calsBurned, String description) {
        this.name = name;
        this.publisher = publisher;
        this.level = level;
        this.exercises = exercises;
        this.calsBurned = calsBurned;
        this.description = description;
    }

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public User getPublisher() {
		return publisher;
	}

	public void setPublisher(User publisher) {
		this.publisher = publisher;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public List<Exercise> getExercises() {
		return exercises;
	}

	public void setExercises(List<Exercise> exercises) {
		this.exercises = exercises;
	}

	public double getCalsBurned() {
		return calsBurned;
	}

	public void setCalsBurned(double calsBurned) {
		this.calsBurned = calsBurned;
	}
	
	public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Workout{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", publisher='" + publisher + '\'' +
                ", level='" + level + '\'' +
                ", exercises=" + exercises +
                ", calsBurned=" + calsBurned +
                ", description='" + description + '\'' +
                '}';
    }
}