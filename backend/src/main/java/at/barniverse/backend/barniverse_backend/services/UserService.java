package at.barniverse.backend.barniverse_backend.services;

import at.barniverse.backend.barniverse_backend.dto.UserDto;
import at.barniverse.backend.barniverse_backend.repository.UserRepository;
import at.barniverse.backend.barniverse_backend.transformer.UserTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

// service with user related functionality
@Service
public class UserService extends BaseService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserTransformer userTransformer;

    // create new user
    public ResponseEntity<Object> addUser(UserDto userDto) {
        return addEntity(userRepository, userTransformer, userDto);
    }

    // get all users
    public ResponseEntity<Object> getUsers() {
        return getEntities(userRepository, userTransformer);
    }

    // get specific user
    public ResponseEntity<Object> getUser(int id) {
        return getEntity(userRepository, userTransformer, id);
    }

    // update specific user
    public ResponseEntity<Object> updateUser(UserDto userDto) {
        return updateEntity(userRepository, userTransformer, userDto);
    }

    // delete specific user
    public ResponseEntity<Object> deleteUser(int id) {
        return deleteEntity(userRepository, id);
    }
}
