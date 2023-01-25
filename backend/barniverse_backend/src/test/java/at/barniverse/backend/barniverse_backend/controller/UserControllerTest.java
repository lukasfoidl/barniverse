package at.barniverse.backend.barniverse_backend.controller;

import at.barniverse.backend.barniverse_backend.dto.ChangePasswordDto;
import at.barniverse.backend.barniverse_backend.dto.UserDto;
import at.barniverse.backend.barniverse_backend.enums.UserState;
import at.barniverse.backend.barniverse_backend.model.User;
import at.barniverse.backend.barniverse_backend.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    UserService service;

    @InjectMocks
    UserController userController;

    MockMvc mockMvc;


      @BeforeEach
     void setup(){
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();

     }



    /*@Test
    void addUser() throws Exception {

        UserDto user = new UserDto();
        user.setFirstname("John");
        user.setLastname("Doe");
        user.setUsername("JonnyDoe123");
        user.setEmail("jonnydoe@test.at");
        user.setPassword("JonnyDoe123");
        user.setState(UserState.active);


        ObjectMapper objectmapper = new ObjectMapper();
        String json = objectmapper.writeValueAsString(user);

        ResponseEntity entity = new ResponseEntity(json, HttpStatus.OK);

        //Mockito.when(service.addUser(any(UserDto.class))).thenReturn(entity);

        mockMvc.perform(post("/api/users").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk())
                .andExpect(content().string(json));
    }*/

    @WithMockUser(roles = "ADMIN")
    @Test
    void getUsers() throws Exception {

        List<UserDto> list = new ArrayList<>();

        UserDto dto = new UserDto();
        dto.setId(1);
        dto.setFirstname("John");
        dto.setLastname("Doe");
        dto.setUsername("JonnyDoe123");
        dto.setEmail("jonnydoe@test.at");
        dto.setPassword("dgsdfg456dzhhmnnlkklghknfghndhgmztuitjl5");
        dto.setState(UserState.active);
        dto.setPicture("test.png");

        UserDto dto2 = new UserDto();
        dto2.setId(2);
        dto2.setFirstname("Alfred");
        dto2.setLastname("Doe");
        dto2.setUsername("Ali12345");
        dto2.setEmail("alidoe@test.at");
        dto2.setPassword("dgsdfg456dzhhmnnlkklghknfghndhgmztuitjl5");
        dto2.setState(UserState.active);
        dto2.setPicture("test2.png");

        list.add(dto);
        list.add(dto2);

        ObjectMapper objectmapper = new ObjectMapper();
        String json = objectmapper.writeValueAsString(list);

        ResponseEntity entity = new ResponseEntity(json, HttpStatus.OK);

        Mockito.when(service.getUsers()).thenReturn(list);

        mockMvc.perform(get("/api/users").accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(json))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").exists());

    }
    @WithMockUser(roles = "USER")
    @Test
    void getUser() throws Exception {

        UserDto dto = new UserDto();
        dto.setId(1);
        dto.setFirstname("John");
        dto.setLastname("Doe");
        dto.setUsername("JonnyDoe123");
        dto.setEmail("jonnydoe@test.at");
        dto.setPassword("dgsdfg456dzhhmnnlkklghknfghndhgmztuitjl5");
        dto.setState(UserState.active);
        dto.setPicture("test.png");

        ObjectMapper objectmapper = new ObjectMapper();
        String json = objectmapper.writeValueAsString(dto);

       // ResponseEntity entity = new ResponseEntity(json, HttpStatus.OK);

        Mockito.when(service.getUser(1)).thenReturn(dto);

        mockMvc.perform(get("/api/users/1").accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(json))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());

    }

    @WithMockUser(roles = "USER")
    @Test
    void updateUser() throws Exception {
        UserDto user = new UserDto();
        user.setId(11);
        user.setFirstname("Test");
        user.setLastname("Test");
        user.setUsername("JonnyDoe123");
        user.setEmail("jonnydoe@test.at");
        user.setPassword("JonnyDoe123");
        user.setState(UserState.active);

        ObjectMapper objectmapper = new ObjectMapper();
        String json = objectmapper.writeValueAsString(user);

        var token = Collections.singletonMap("jwt-token", "token");

        ResponseEntity entity = new ResponseEntity(json,HttpStatus.OK);

        Mockito.when(service.updateUser(any(UserDto.class))).thenReturn(token);

        mockMvc.perform(put("/api/users").contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void deleteUser() {
    }


    @Test
    void deleteWithState() throws Exception {

        mockMvc.perform(put("/api/users/11/deleteWithState"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void deleteWithStateInvalidId() throws Exception {

        mockMvc.perform(put("/api/users/11/deleteWithState"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @WithMockUser(roles = "USER")
    @Test
    void changePassword() throws Exception  {

        ChangePasswordDto dto = new ChangePasswordDto();
        dto.setId(10);
        dto.setPassword("dsfasgdasfasfashrtrzert45236");
        ObjectMapper objectmapper = new ObjectMapper();
        String json = objectmapper.writeValueAsString(dto);


        ResponseEntity entity = new ResponseEntity(json,HttpStatus.OK);



        mockMvc.perform(put("/api/users/changePassword").contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print())
                .andExpect(status().isOk());
    }
    @WithMockUser(roles = "ADMIN")
    @Test
    void toggleAdmin() throws Exception {

          mockMvc.perform(put("/api/users/10/toggleAdmin"))
                  .andDo(print())
                  .andExpect(status().isOk());
    }

    @WithMockUser(roles = "ADMIN")
    @Test
    void toggleState() throws Exception {

        Mockito.when(service.toggleState(11)).thenReturn(UserState.active);

        mockMvc.perform(put("/api/users/11/toggleState/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("\"" + UserState.active.toString()+ "\"" ));

    }
    @Test
    void wrongApiRequest () throws Exception{
          // testing typo "user" --> working "users"
          mockMvc.perform(get("/api/user/10")).andDo(print()).andExpect(status().isNotFound()).andExpect(content().string(""));

    }
}