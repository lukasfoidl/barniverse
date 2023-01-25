package at.barniverse.backend.barniverse_backend.services;

import at.barniverse.backend.barniverse_backend.dto.AuthDto;
import at.barniverse.backend.barniverse_backend.dto.LoginCredentialsDto;
import at.barniverse.backend.barniverse_backend.dto.UserDto;
import at.barniverse.backend.barniverse_backend.enums.Role;
import at.barniverse.backend.barniverse_backend.enums.UserState;
import at.barniverse.backend.barniverse_backend.exception.BarniverseException;
import at.barniverse.backend.barniverse_backend.model.User;
import at.barniverse.backend.barniverse_backend.repository.UserRepository;
import at.barniverse.backend.barniverse_backend.security.JWTUtil;
import at.barniverse.backend.barniverse_backend.transformer.UserTransformer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    AuthenticationManager authenticationManager;



    @Mock
    UserRepository userRepository;
    @Mock
    UserTransformer userTransformer;
    @Mock
    UserValidationService userValidationService;
    @Mock
    PasswordEncoder passwordEncoder;
    @Mock
    JWTUtil jwtUtil;
    @InjectMocks
    AuthService authService;

    AuthDto authDto;

    UserDto userDto ;

    User user;

    LoginCredentialsDto loginCredentialsDto;


    @BeforeEach
    void setup (){
        loginCredentialsDto = new LoginCredentialsDto();
        loginCredentialsDto.setEmail("jonnydoe@test1.at");
        loginCredentialsDto.setPassword("dgsdfg456dzhhmnnlkklghknfghndhgmztuitjl5");

        authDto = new AuthDto();
        authDto.setEmail("jonnydoe@test1.at");
        authDto.setUuid("1");
        authDto.setRole(Role.ROLE_USER.toString());
        authDto.setUsername("JonnyDoe1231");

        user = new User();
        user.setId(1);
        user.setFirstname("John1");
        user.setLastname("Doe1");
        user.setUsername("JonnyDoe1231");
        user.setEmail("jonnydoe@test1.at");
        user.setPassword("dgsdfg456dzhhmnnlkklghknfghndhgmztuitjl5");
        user.setState(UserState.active);
        user.setIsAdmin(false);
        user.setPicture("test1.png");

        userDto = new UserDto();
        userDto.setId(1);
        userDto.setFirstname("John1");
        userDto.setLastname("Doe1");
        userDto.setUsername("JonnyDoe1231");
        userDto.setEmail("jonnydoe@test1.at");
        userDto.setPassword("dgsdfg456dzhhmnnlkklghknfghndhgmztuitjl5");

        userDto.setPicture("test1.png");

    }

    @Test
    void register() throws BarniverseException {
    given(userTransformer.convertToEntity(userDto)).willReturn(user);
    given(userRepository.save(user)).willReturn(user);
    given(userTransformer.convertToDto(user)).willReturn(userDto);
    given(jwtUtil.getToken(any(AuthDto.class))).willReturn(Collections.singletonMap("jwt-token", "token"));
    given(passwordEncoder.encode(user.getPassword())).willReturn(user.getPassword());

    Map<String, String> token =  authService.register(userDto);

    assertEquals(Collections.singletonMap("jwt-token", "token"), token);
    verify(jwtUtil, times(1)).getToken(any(AuthDto.class));
    verify(passwordEncoder, times(1)).encode(any(String.class));

    }

    @Test
    void login() throws BarniverseException {
        given(userRepository.findByEmail(authDto.getEmail())).willReturn(user);
        given(jwtUtil.getToken(any(authDto.getClass()))).willReturn(Collections.singletonMap("jwt-token", "token"));

        Map <String, String> token = authService.login(loginCredentialsDto);

        verify(jwtUtil,times(1)).getToken(any(AuthDto.class));

    }
}