package at.barniverse.backend.barniverse_backend.controller;

import at.barniverse.backend.barniverse_backend.dto.LoginCredentialsDto;
import at.barniverse.backend.barniverse_backend.dto.UserDto;
import at.barniverse.backend.barniverse_backend.enums.UserState;
import at.barniverse.backend.barniverse_backend.services.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    AuthService service;

    @InjectMocks
    AuthController authController;

    MockMvc mockMvc;

    @BeforeEach
    void setup(){
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
    }

    @Test
    @DisplayName("Register API")
    void register() throws Exception {
        UserDto user = new UserDto();
        user.setFirstname("John");
        user.setLastname("Doe");
        user.setUsername("JonnyDoe123");
        user.setEmail("jonnydoe@test.at");
        user.setPassword("dgsdfg456dzhhmnnlkklghknfghndhgmztuitjl5");
        user.setState(UserState.active);
        user.setPicture("test.png");

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(user);

        var token = Collections.singletonMap("jwt-token", "token");

        ResponseEntity entity = new ResponseEntity(HttpStatus.OK);

        Mockito.when(service.register(any(UserDto.class))).thenReturn(token);

        mockMvc.perform(post("/api/register").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk());




    }


    @Test
    @DisplayName("Register without Dto API")
    void registerWithNoDto() throws Exception {

        //Expected to send bad Request because Dto is not provided in post Request
        mockMvc.perform(post("/api/register"))
                .andExpect(status().isBadRequest());

    }

    @Test
    @DisplayName("Login API")
    void login() throws Exception {

        LoginCredentialsDto dto = new LoginCredentialsDto();
        dto.setEmail("jonny@test.at");
        dto.setPassword("asdfasdfccvbfdgz123");

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(dto);


         var token = Collections.singletonMap("jwt-token", "token");

        Mockito.when(service.login(any(LoginCredentialsDto.class))).thenReturn(token);

        mockMvc.perform(post("/api/login").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk());

    }


    @Test
    @DisplayName("Login without Dto API")
    void loginWithNoDto() throws Exception {

        //Expected to send bad Request because Dto is not provided in post Request
        mockMvc.perform(post("/api/login"))
                .andExpect(status().isBadRequest());

    }
}