package at.barniverse.backend.barniverse_backend.repository;

import at.barniverse.backend.barniverse_backend.enums.UserState;
import at.barniverse.backend.barniverse_backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

/**
 * basic repository for user entity
 * will be auto implemented by Spring into a Bean called userRepository
 */
public interface UserRepository extends CrudRepository<User, Integer> {

    User findByEmail(String email);
    List<User> findAllByState(UserState state);

    boolean existsById(int id);
    boolean existsByIdAndState(int id, UserState state);

}
