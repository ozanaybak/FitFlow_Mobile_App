package documents;

public class ExerciseDTO {

    private String id;
    private String bodyPart;
    private String name;

    // Constructors, getters, setters...

    public ExerciseDTO(String id, String name, String bodyPart) {
        this.id = id;
        this.bodyPart = bodyPart;
        this.name = name;
    }

    public String getId() {
        return id;
    }
    
    public void setId(String id2) {
		this.id = id2;
		
	}

    public String getBodyPart() {
        return bodyPart;
    }

    public void setBodyPart(String bodyPart) {
        this.bodyPart = bodyPart;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

	
}
