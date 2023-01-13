package at.barniverse.backend.barniverse_backend.services;

import at.barniverse.backend.barniverse_backend.dto.AuthDto;
import at.barniverse.backend.barniverse_backend.dto.ChangePasswordDto;
import at.barniverse.backend.barniverse_backend.dto.ProductDto;
import at.barniverse.backend.barniverse_backend.dto.UserDto;
import at.barniverse.backend.barniverse_backend.enums.ProductState;
import at.barniverse.backend.barniverse_backend.enums.RoleConverter;
import at.barniverse.backend.barniverse_backend.enums.UserState;
import at.barniverse.backend.barniverse_backend.model.User;
import at.barniverse.backend.barniverse_backend.repository.UserRepository;
import at.barniverse.backend.barniverse_backend.security.JWTUtil;
import at.barniverse.backend.barniverse_backend.transformer.UserTransformer;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * service with user related functionality
 */
@Service
public class UserService extends BaseService {

    @Autowired private UserRepository userRepository;
    @Autowired private UserTransformer userTransformer;
    @Autowired private UserValidationService userValidationService;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private JWTUtil jwtUtil;

    /**
     * add a user to the database
     * @param userDto dto which should be saved
     * @return response with corresponding status code and error message in case of failure
     */
    public ResponseEntity<Object> addUser(UserDto userDto) {
        return addEntity(userRepository, userTransformer, userValidationService, userDto);
    }

    /**
     * get all saved users from the database which do not have state deleted
     * @return response with corresponding status code and loaded user dtos or error message in case of failure
     */
    public ResponseEntity<Object> getUsers() {
        ResponseEntity<Object> response = getEntities(userRepository, userTransformer);
        if (response.getStatusCode() != HttpStatus.OK) {
            return response;
        }
        List<UserDto> userDtoList = (List<UserDto>) response.getBody();
        List<UserDto> resultList = new ArrayList<>();
        for (UserDto userDto : userDtoList) {
            if (userDto.getState() != UserState.deleted) {
                resultList.add(userDto);
            }
        }
        return new ResponseEntity<>(resultList, HttpStatus.OK);
    }

    /**
     * get specific user from the database
     * @param id id of the specific user
     * @return response with corresponding status code and loaded user dto or error message in case of failure
     */
    public ResponseEntity<Object> getUser(int id) {
        return getEntityAsDto(userRepository, userTransformer, id);
    }

    /**
     * update specific user in the database
     * @param userDto dto which should be updated (with id)
     * @return response with corresponding status code and error message in case of failure
     */
    public ResponseEntity<Object> updateUser(UserDto userDto) {
        ResponseEntity<Object> response = updateEntity(userRepository, userTransformer, userValidationService, userDto);
        if (response.getStatusCode() != HttpStatus.OK) {
            return response;
        }
        //TODO: user gets saved an loaded again for data for token (especially the role) => inefficient
        ResponseEntity<Object> dbResponse = getEntity(userRepository, userDto.getId());
        if (response.getStatusCode() != HttpStatus.OK) {
            return response;
        }
        User user = (User) dbResponse.getBody();
        return jwtUtil.getToken(new AuthDto(user.getEmail(), user.getUsername(), RoleConverter.getRole(user.getIsAdmin()).toString(), Integer.toString(user.getId())));
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
     * deletes a user with state deleted
     * @param id id of the specific user
     * @return response with corresponding status code and error message in case of failure
     */
    public ResponseEntity<Object> deleteWithState(int id) {
        ResponseEntity<Object> response = getEntity(userRepository, id);
        if (response.getStatusCode() != HttpStatus.OK) {
            return response;
        }
        User user = (User) response.getBody();
        user.setState(UserState.deleted);
        return save(userRepository, user);
    }

    /**
     * changing password of specific user
     * @param changePasswordDto change password dto sent from the client
     * @return response with corresponding status code and error message in case of failure
     */
    public ResponseEntity<Object> changePassword(ChangePasswordDto changePasswordDto) {
        ResponseEntity<Object> response = getEntity(userRepository, changePasswordDto.getId());
        if (response.getStatusCode() != HttpStatus.OK) {
            return response;
        }
        User user = (User) response.getBody();
        user.setPassword(changePasswordDto.getPassword());
        ResponseEntity<Object> validationResponse = userValidationService.validateEntity(user);
        if (validationResponse.getStatusCode() != HttpStatus.OK) {
            return validationResponse;
        }
        user.setPassword(passwordEncoder.encode(changePasswordDto.getPassword()));
        return save(userRepository, user);
    }

    /**
     * toggles the admin value of a specific user (give admin rights or take admin rights)
     * @param id id of the specific user
     * @return response with corresponding status code and error message in case of failure
     */
    public ResponseEntity<Object> toggleAdmin(int id) {
        ResponseEntity<Object> response = getEntity(userRepository, id);
        if (response.getStatusCode() != HttpStatus.OK) {
            return response;
        }
        User user = (User) response.getBody();
        user.setIsAdmin(!user.getIsAdmin());
        return save(userRepository, user);
    }

    /**
     * toggles the user state of a specific user (deactivate or activate)
     * @param id id of the specific user
     * @return response with corresponding status code and error message in case of failure
     */
    public ResponseEntity<Object> toggleState(int id) {
        ResponseEntity<Object> response = getEntity(userRepository, id);
        if (response.getStatusCode() != HttpStatus.OK) {
            return response;
        }
        User user = (User) response.getBody();
        if (user.getState() == UserState.active) {
            user.setState(UserState.blocked);
        } else if (user.getState() == UserState.blocked) {
            user.setState(UserState.active);
        } else {
           return new ResponseEntity<>("User is deleted and cannot be changed!", HttpStatus.BAD_REQUEST);
        }
        ResponseEntity<Object> userResponse = saveAndGet(userRepository, userTransformer, user);
        if (userResponse.getStatusCode() != HttpStatus.OK) {
            return userResponse;
        }
        UserDto userDto = (UserDto) userResponse.getBody();
        return new ResponseEntity<>(userDto.getState(), HttpStatus.OK);
    }
}
