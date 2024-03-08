package repos;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import documents.Workout;

public interface workoutRepo extends MongoRepository<Workout, String>{
	Optional<Workout> findById(String id);
}