package at.barniverse.backend.barniverse_backend.repository;

import at.barniverse.backend.barniverse_backend.enums.UserState;
import at.barniverse.backend.barniverse_backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

/**
 * basic repository for user entity
 */
public interface UserRepository extends CrudRepository<User, Integer> {

    /**
     * get user with a specific email
     * @param email email of a specific user
     * @return users with a specific email
     */
    User findByEmail(String email);

    /**
     * get all users with a state or with another state
     * @param state1 state which should be filtered
     * @param state2 other state which should be filtered too
     * @return users with the given states
     */
    List<User> findAllByStateOrState(UserState state1, UserState state2);

    /**
     * check if user with specific id exists
     * @param id id of the specific user
     * @return true if user exists, otherwise false
     */
    boolean existsById(int id);

    /**
     * check if user exists with a specific id and a specific state
     * @param id id of the specific user
     * @param state state which should be filtered
     * @return true if user exists, otherwise false
     */
    boolean existsByIdAndState(int id, UserState state);

}
