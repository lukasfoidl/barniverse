package at.barniverse.backend.barniverse_backend.controller;

import at.barniverse.backend.barniverse_backend.dto.AuthDto;
import at.barniverse.backend.barniverse_backend.dto.LoginCredentialsDto;
import at.barniverse.backend.barniverse_backend.dto.UserDto;
import at.barniverse.backend.barniverse_backend.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static at.barniverse.backend.barniverse_backend.configuration.Context.CORS_ORIGINS;

/**
 * controller with authentication routing
 */
@RestController
@CrossOrigin(origins = CORS_ORIGINS)
@RequestMapping(path = "/api")
public class AuthController {

    @Autowired private AuthService authService;

    /**
     * register a new user (add new user to the database)
     * @param userDto object sent from the client
     * @return response with the corresponding status code and a jwt token or error message in case of failure
     */
    @PostMapping(path="/register")
    public ResponseEntity<Object> register(@RequestBody UserDto userDto) {
        return authService.register(userDto);
    }

    /**
     * authenticate a user
     * @param loginCredentialsDto login credentials sent from the client
     * @return response with the corresponding status code and a jwt token if the user gets authenticated <br>
     * or error message in case of failure
     */
    @PostMapping(path="/login")
    public ResponseEntity<Object> login(@RequestBody LoginCredentialsDto loginCredentialsDto) {
        return authService.login(loginCredentialsDto);
    }
}
