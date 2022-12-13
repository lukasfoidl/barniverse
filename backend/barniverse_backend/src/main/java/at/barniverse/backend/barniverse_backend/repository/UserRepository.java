package at.barniverse.backend.barniverse_backend.repository;

import at.barniverse.backend.barniverse_backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * basic repository for user entity
 * will be auto implemented by Spring into a Bean called userRepository
 */
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);

}
