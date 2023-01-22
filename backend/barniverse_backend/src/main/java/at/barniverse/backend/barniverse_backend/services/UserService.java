package at.barniverse.backend.barniverse_backend.services;

import at.barniverse.backend.barniverse_backend.exception.BarniverseException;
import at.barniverse.backend.barniverse_backend.dto.AuthDto;
import at.barniverse.backend.barniverse_backend.dto.ChangePasswordDto;
import at.barniverse.backend.barniverse_backend.dto.UserDto;
import at.barniverse.backend.barniverse_backend.enums.RoleConverter;
import at.barniverse.backend.barniverse_backend.enums.UserState;
import at.barniverse.backend.barniverse_backend.model.User;
import at.barniverse.backend.barniverse_backend.repository.UserRepository;
import at.barniverse.backend.barniverse_backend.security.JWTUtil;
import at.barniverse.backend.barniverse_backend.transformer.UserTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static at.barniverse.backend.barniverse_backend.configuration.Context.DATABASE_ERROR;

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
     * get all saved users from the database which do not have state deleted
     * @return response with corresponding status code and loaded user dtos or error message in case of failure
     */
    public List<UserDto> getUsers() throws BarniverseException {
        List<User> users;
        try {
            users = userRepository.findAllByState(UserState.active);
        } catch (Exception exception) {
            throw new BarniverseException(List.of(DATABASE_ERROR), HttpStatus.INTERNAL_SERVER_ERROR, exception);
        }

        return convertToDto(userTransformer, users);
        /*
        comment: added Code for testing

        ObjectMapper objectMapper = new ObjectMapper();
        String json = "";
        try{
            json = objectMapper.writeValueAsString(resultList);
        }catch (Exception e){
            System.out.println(e);
        }
        ends here + json in entity body instead of resultList add
        */
    }

    /**
     * get specific user from the database
     * @param id id of the specific user
     * @return response with corresponding status code and loaded user dto or error message in case of failure
     */
    public UserDto getUser(int id) throws BarniverseException {
        return getEntityAsDto(userRepository, userTransformer, id);
    }

    /**
     * update specific user in the database
     * @param userDto dto which should be updated (with id)
     * @return response with corresponding status code and error message in case of failure
     */
    public Map<String, String> updateUser(UserDto userDto) throws BarniverseException {
        updateEntity(userRepository, userTransformer, userValidationService, userDto, userDto.getId());

        //TODO: user gets saved an loaded again for data for token (especially the role) => inefficient
        User user = getEntity(userRepository, userDto.getId());
        return jwtUtil.getToken(new AuthDto(user.getEmail(), user.getUsername(), RoleConverter.getRole(user.getIsAdmin()).toString(), Integer.toString(user.getId())));
    }

    /**
     * deletes a user with state deleted
     * @param id id of the specific user
     * @return response with corresponding status code and error message in case of failure
     */
    public void deleteWithState(int id) throws BarniverseException {
        User user = getEntity(userRepository, id);
        user.setState(UserState.deleted);
        save(userRepository, user);
    }

    /**
     * changing password of specific user
     * @param changePasswordDto change password dto sent from the client
     * @return response with corresponding status code and error message in case of failure
     */
    public void changePassword(ChangePasswordDto changePasswordDto) throws BarniverseException {
        User user = getEntity(userRepository, changePasswordDto.getId());
        user.setPassword(changePasswordDto.getPassword());
        userValidationService.validateEntity(user);
        user.setPassword(passwordEncoder.encode(changePasswordDto.getPassword()));
        save(userRepository, user);
    }

    /**
     * toggles the admin value of a specific user (give admin rights or take admin rights)
     * @param id id of the specific user
     * @return response with corresponding status code and error message in case of failure
     */
    public void toggleAdmin(int id) throws BarniverseException {
        User user = getEntity(userRepository, id);
        user.setIsAdmin(!user.getIsAdmin());
        save(userRepository, user);
    }

    /**
     * toggles the user state of a specific user (deactivate or activate)
     * @param id id of the specific user
     * @return response with corresponding status code and error message in case of failure
     */
    public UserState toggleState(int id) throws BarniverseException {
        User user = getEntity(userRepository, id);

        UserState state;
        if (user.getState() == UserState.active) {
            state = UserState.blocked;
        } else if (user.getState() == UserState.blocked) {
            state = UserState.active;
        } else {
            throw new BarniverseException(List.of("User is deleted and cannot be changed!"), HttpStatus.FORBIDDEN, null);
        }
        user.setState(state);
        save(userRepository, user);

        return state;
    }
}
