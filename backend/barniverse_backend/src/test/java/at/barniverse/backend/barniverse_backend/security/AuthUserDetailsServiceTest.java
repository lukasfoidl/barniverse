package at.barniverse.backend.barniverse_backend.security;

import at.barniverse.backend.barniverse_backend.enums.Role;
import at.barniverse.backend.barniverse_backend.enums.UserState;
import at.barniverse.backend.barniverse_backend.model.User;
import at.barniverse.backend.barniverse_backend.repository.UserRepository;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AuthUserDetailsServiceTest {

    @Mock
    UserRepository userRepository;
    @InjectMocks
    AuthUserDetailsService authUserDetailsService;

    User user;

    UserDetails userDetails;


    @BeforeEach
    void setup(){
        user = new User();
        user.setId(1);
        user.setFirstname("John1");
        user.setLastname("Doe1");
        user.setUsername("JonnyDoe1231");
        user.setEmail("jonnydoe@test1.at");
        user.setPassword("dgsdfg456dzhhmnnlkklghknfghndhgmztuitjl5");
        user.setState(UserState.active);
        user.setPicture("test1.png");



    }

    @Test
    void loadUserByUsername() {

        given(userRepository.findByEmail(user.getEmail())).willReturn(user);

        userDetails = authUserDetailsService.loadUserByUsername(user.getEmail());

        verify(userRepository, times(1)).findByEmail(user.getEmail());
        System.out.println(userDetails.getAuthorities());
        assertEquals("[" + Role.ROLE_USER + "]",userDetails.getAuthorities().toString());
        //UserDetails getUsername returns email
        assertEquals(user.getEmail(), userDetails.getUsername());

    }
    @Test
    void loadUserByUsernameFalse() {

        given(userRepository.findByEmail(user.getEmail())).willReturn(null);

        UsernameNotFoundException thrown = Assertions.assertThrows(UsernameNotFoundException.class, () -> {
            userDetails = authUserDetailsService.loadUserByUsername(user.getEmail());
        });

        verify(userRepository, times(1)).findByEmail(user.getEmail());

        assertEquals("Could not find User with email: " + user.getEmail(), thrown.getMessage());

    }
}