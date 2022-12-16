package at.barniverse.backend.barniverse_backend.services;

import at.barniverse.backend.barniverse_backend.dto.UserDto;
import at.barniverse.backend.barniverse_backend.enums.UserState;
import at.barniverse.backend.barniverse_backend.model.User;
import at.barniverse.backend.barniverse_backend.repository.UserRepository;
import at.barniverse.backend.barniverse_backend.transformer.UserTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static at.barniverse.backend.barniverse_backend.configuration.Context.DATABASE_ERROR;
import static at.barniverse.backend.barniverse_backend.configuration.Context.INVALID_ID;

/**
 * service with user related functionality
 */
@Service
public class UserService extends BaseService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserTransformer userTransformer;

    @Autowired
    private UserValidationService userValidationService;

    /**
     * add a user to the database
     * @param userDto dto which should be saved
     * @return response with corresponding status code and added user or error message in case of failure
     */
    public ResponseEntity<Object> addUser(UserDto userDto) {
        return addEntity(userRepository, userTransformer, userValidationService, userDto);
    }

    /**
     * get all saved users from the database
     * @return response with corresponding status code and loaded user dtos or error message in case of failure
     */
    public ResponseEntity<Object> getUsers() {
        return getEntities(userRepository, userTransformer);
    }

    /**
     * get specific user from the database
     * @param id id of the specific user
     * @return response with corresponding status code and loaded user dto or error message in case of failure
     */
    public ResponseEntity<Object> getUser(int id) {
        return getEntity(userRepository, userTransformer, id);
    }

    /**
     * update specific user in the database
     * @param userDto dto which should be updated (with id)
     * @return response with corresponding status code and updated user or error message in case of failure
     */
    public ResponseEntity<Object> updateUser(UserDto userDto) {
        return updateEntity(userRepository, userTransformer, userValidationService, userDto);
    }

    /**
     * delete specific user from the database
     * @param id id of the specific user
     * @return response with corresponding status code and error message in case of failure
     */
    public ResponseEntity<Object> deleteUser(int id) {
        return deleteEntity(userRepository, id);
    }

    /**
     * deactivate specific user
     * @param id id of the specific user
     * @return response with corresponding status code and updated user or error message in case of failure
     */
    public ResponseEntity<Object> deactivateUser(int id) {
        Optional<User> user;
        try {
            user = userRepository.findById(id);
        } catch (Exception exception) {
            return new ResponseEntity<>(DATABASE_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (user.isEmpty() || user.get().getId() != id) {
            return new ResponseEntity<>(INVALID_ID, HttpStatus.BAD_REQUEST);
        }
        user.get().setState(UserState.deleted);
        try {
            userRepository.save(user.get());
        } catch (Exception exception) {
            return new ResponseEntity<>(DATABASE_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(user.get(), HttpStatus.OK);
    }
}
