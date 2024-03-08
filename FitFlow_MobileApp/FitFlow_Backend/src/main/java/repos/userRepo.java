package repos;
import java.util.Optional;

import javax.management.Query;

import org.springframework.data.mongodb.repository.MongoRepository;
import documents.User;
import documents.Workout;

public interface userRepo extends MongoRepository<User, String> {
	User findByEmail(String email);
	void deleteByEmail(String email);
	Optional<User> findById(String id);
	
}
