package at.barniverse.backend.barniverse_backend.controller;

import at.barniverse.backend.barniverse_backend.dto.AuthDto;
import at.barniverse.backend.barniverse_backend.dto.LoginCredentialsDto;
import at.barniverse.backend.barniverse_backend.dto.UserDto;
import at.barniverse.backend.barniverse_backend.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static at.barniverse.backend.barniverse_backend.configuration.Context.CORS_ORIGINS;

@RestController
@CrossOrigin(origins = CORS_ORIGINS)
@RequestMapping(path = "/api")
public class AuthController {

    @Autowired private AuthService authService;

    @PostMapping(path="/register")
    public ResponseEntity<Object> register(@RequestBody UserDto userDto) {
        return authService.register(userDto);
    }

    @PostMapping(path="/login")
    public ResponseEntity<Object> login(@RequestBody LoginCredentialsDto loginCredentialsDto) {
        return authService.login(loginCredentialsDto);
    }
}
