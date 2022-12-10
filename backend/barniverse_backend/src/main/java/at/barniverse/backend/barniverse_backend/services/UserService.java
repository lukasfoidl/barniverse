package at.barniverse.backend.barniverse_backend.services;

import at.barniverse.backend.barniverse_backend.dto.UserDto;
import at.barniverse.backend.barniverse_backend.repository.UserRepository;
import at.barniverse.backend.barniverse_backend.transformer.UserTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

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
     * @return response with corresponding status code and error message in case of failure
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
     * @return response with corresponding status code and error message in case of failure
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
}
