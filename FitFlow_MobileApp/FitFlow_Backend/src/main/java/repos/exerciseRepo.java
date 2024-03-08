package repos;
import documents.Exercise;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface exerciseRepo extends MongoRepository<Exercise, String> {
	Optional<Exercise> findById(String id);
		
}
