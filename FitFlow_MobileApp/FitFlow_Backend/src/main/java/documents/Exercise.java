package documents;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "exercises")
public class Exercise {

    @Id
    private String id;

    private String bodyPart;
    private int sets;
    private int reps;
    private String level;
    private String type; // Cardio, Gym, Calisthenics, etc.
    
    private String name;
    private String description;
    

    // Constructors, getters, setters, and additional methods...

    // Constructors
    public Exercise() {
        // TODO Default constructor for Spring Data MongoDB
    }

    public Exercise(String bodyPart, int sets, int reps, String level, String type, String name, String description) {
        this.bodyPart = bodyPart;
        this.sets = sets;
        this.reps = reps;
        this.level = level;
        this.type = type;
        this.name = name;
        this.description = description;
    }

    

	public String getId() {
		return id;
	}


	public String getBodyPart() {
		return bodyPart;
	}

	public void setBodyPart(String bodyPart) {
		this.bodyPart = bodyPart;
	}

	public int getSets() {
		return sets;
	}

	public void setSets(int sets) {
		this.sets = sets;
	}

	public int getReps() {
		return reps;
	}

	public void setReps(int reps) {
		this.reps = reps;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


	@Override
	public String toString() {
        return "Exercise{" +
                "id='" + id + '\'' +
                ", bodyPart='" + bodyPart + '\'' +
                ", sets=" + sets +
                ", reps=" + reps +
                ", level='" + level + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

}
