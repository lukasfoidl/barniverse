package at.barniverse.backend.barniverse_backend.services;

import at.barniverse.backend.barniverse_backend.dto.AuthDto;
import at.barniverse.backend.barniverse_backend.dto.LoginCredentialsDto;
import at.barniverse.backend.barniverse_backend.dto.UserDto;
import at.barniverse.backend.barniverse_backend.enums.Role;
import at.barniverse.backend.barniverse_backend.enums.RoleConverter;
import at.barniverse.backend.barniverse_backend.enums.UStatus;
import at.barniverse.backend.barniverse_backend.model.User;
import at.barniverse.backend.barniverse_backend.repository.UserRepository;
import at.barniverse.backend.barniverse_backend.security.JWTUtil;
import at.barniverse.backend.barniverse_backend.transformer.UserTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

import static at.barniverse.backend.barniverse_backend.configuration.Context.DATABASE_ERROR;
import static at.barniverse.backend.barniverse_backend.configuration.Context.INVALID_LOGIN_CREDENTIALS;

@Service
public class AuthService extends BaseService {

    @Autowired private JWTUtil jwtUtil;
    @Autowired private AuthenticationManager authManager;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private UserRepository userRepository;
    @Autowired private UserTransformer userTransformer;
    @Autowired private UserValidationService userValidationService;

    // TODO: after successful creation of user token is returned immediately, no further checks if user is real
    public ResponseEntity<Object> register(UserDto userDto) {
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userDto.setStatus(UStatus.active);
        ResponseEntity<Object> response = addEntity(userRepository, userTransformer, userValidationService, userDto);
        if (response.getStatusCode() == HttpStatus.OK) {
            String token = jwtUtil.generateToken(new AuthDto (userDto.getEmail(), userDto.getUsername(), Role.ROLE_USER.toString()));
            return new ResponseEntity<>(Collections.singletonMap("jwt-token", token), HttpStatus.OK);
        }
        return response;
    }

    public ResponseEntity<Object> login(LoginCredentialsDto loginCredentialsDto) {
        try {
            UsernamePasswordAuthenticationToken authInputToken =
                    new UsernamePasswordAuthenticationToken(loginCredentialsDto.getEmail(), loginCredentialsDto.getPassword());
            authManager.authenticate(authInputToken);
            User user = getUser(loginCredentialsDto.getEmail());
            if (user == null) {
                return new ResponseEntity<>(DATABASE_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            String token = jwtUtil.generateToken(new AuthDto(user.getEmail(), user.getUsername(), RoleConverter.getRole(user.isAdmin()).toString()));
            return new ResponseEntity<>(Collections.singletonMap("jwt-token", token), HttpStatus.OK);
        } catch (AuthenticationException authExc) {
            return new ResponseEntity<>(INVALID_LOGIN_CREDENTIALS, HttpStatus.UNAUTHORIZED);
        }
    }

    private User getUser(String email) {
        Optional<User> user;
        try {
            user = userRepository.findByEmail(email);
            // cannot be empty because authentication happens before
        } catch (Exception exception) {
            return null;
        }
        return user.get();
    }
}
