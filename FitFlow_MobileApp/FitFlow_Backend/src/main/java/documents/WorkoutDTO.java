package documents;

public class WorkoutDTO {

    private String id;
    private String name;
    private String publisherName;

    // Constructors, getters, setters, and additional methods...

    // Constructors
    public WorkoutDTO() {
        // TODO Default constructor
    }

    public WorkoutDTO(String id, String name, String publisherName) {
        this.id = id;
        this.name = name;
        this.publisherName = publisherName;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPublisherName() {
        return publisherName;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }

    // Additional methods if needed
}

