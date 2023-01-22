package at.barniverse.backend.barniverse_backend.services;

import at.barniverse.backend.barniverse_backend.exception.BarniverseException;
import at.barniverse.backend.barniverse_backend.dto.AuthDto;
import at.barniverse.backend.barniverse_backend.dto.LoginCredentialsDto;
import at.barniverse.backend.barniverse_backend.dto.UserDto;
import at.barniverse.backend.barniverse_backend.enums.Role;
import at.barniverse.backend.barniverse_backend.enums.RoleConverter;
import at.barniverse.backend.barniverse_backend.enums.UserState;
import at.barniverse.backend.barniverse_backend.model.User;
import at.barniverse.backend.barniverse_backend.repository.UserRepository;
import at.barniverse.backend.barniverse_backend.security.JWTUtil;
import at.barniverse.backend.barniverse_backend.transformer.UserTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static at.barniverse.backend.barniverse_backend.configuration.Context.*;

/**
 * service for authentication
 */
@Service
public class AuthService extends BaseService {

    @Autowired private JWTUtil jwtUtil;
    @Autowired private AuthenticationManager authManager;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private UserRepository userRepository;
    @Autowired private UserTransformer userTransformer;
    @Autowired private UserValidationService userValidationService;

    // TODO: after successful creation of user token is returned immediately, no further checks if user is real
    /**
     * register a new user (add new user to the database)
     * @param userDto object sent from the client
     * @return response with the corresponding status code and a jwt token or error message in case of failure
     */
    public Map<String, String> register(UserDto userDto) throws BarniverseException {
        userDto.setState(UserState.active);
        userDto.setIsAdmin(false);
        User user = userTransformer.convertToEntity(userDto);

        // validate before password gets hashed
        userValidationService.validateEntity(user);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        UserDto dbUserDto = saveAndGetDto(userRepository, userTransformer, user);
        return jwtUtil.getToken(new AuthDto (dbUserDto.getEmail(), dbUserDto.getUsername(), Role.ROLE_USER.toString(), Integer.toString(dbUserDto.getId())));
    }

    /**
     * authenticate a user
     * @param loginCredentialsDto login credentials sent from the client
     * @return response with the corresponding status code and a jwt token if the user gets authenticated <br>
     * or error message in case of failure
     */
    public Map<String, String> login(LoginCredentialsDto loginCredentialsDto) throws BarniverseException {
        try {
            UsernamePasswordAuthenticationToken authInputToken =
                    new UsernamePasswordAuthenticationToken(loginCredentialsDto.getEmail(), loginCredentialsDto.getPassword());
            authManager.authenticate(authInputToken);
            User user = getUser(loginCredentialsDto.getEmail());

            if (user.getState() != UserState.active) {
                throw new BarniverseException(List.of(INVALID_STATE), HttpStatus.FORBIDDEN, null);
            }
            return jwtUtil.getToken(new AuthDto(user.getEmail(), user.getUsername(), RoleConverter.getRole(user.getIsAdmin()).toString(), Integer.toString(user.getId())));
        } catch (AuthenticationException authExc) {
            throw new BarniverseException(List.of(INVALID_LOGIN_CREDENTIALS), HttpStatus.UNAUTHORIZED, authExc);
        }
    }

    /**
     * extension method which loads a user from the database with its email
     * @param email email of the user which needs to be loaded
     * @return user entity
     */
    private User getUser(String email) throws BarniverseException {
        User user;
        try {
            user = userRepository.findByEmail(email);
            // cannot be empty because authentication happens before
        } catch (Exception exception) {
            throw new BarniverseException(List.of(DATABASE_ERROR), HttpStatus.INTERNAL_SERVER_ERROR, exception);
        }
        return user;
    }
}
