package at.barniverse.backend.barniverse_backend.controller;

import at.barniverse.backend.barniverse_backend.dto.ChangePasswordDto;
import at.barniverse.backend.barniverse_backend.dto.UserDto;
import at.barniverse.backend.barniverse_backend.enums.UserState;
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



    @Test
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

        Mockito.when(service.addUser(any(UserDto.class))).thenReturn(entity);

        mockMvc.perform(post("/api/users").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk())
                .andExpect(content().string(json));
    }

    @WithMockUser(roles = "ADMIN")
    @Test
    void getUsers() throws Exception {
        //
        String jsonList = "[{\"id\":1,\"firstname\":\"Lukas\",\"lastname\":\"Foidl\",\"username\":\"lukasfoidl\",\"email\":\"wi20b044@technikum-wien.at\",\"password\":null,\"picture\":\"\",\"isAdmin\":true,\"state\":\"active\"},{\"id\":2,\"firstname\":\"Nils\",\"lastname\":\"Petsch\",\"username\":\"nilspetsch\",\"email\":\"wi20b062@technikum-wien.at\",\"password\":null,\"picture\":\"\",\"isAdmin\":true,\"state\":\"active\"},{\"id\":3,\"firstname\":\"Admin\",\"lastname\":\"Admin\",\"username\":\"admin\",\"email\":\"admin@barniverse.at\",\"password\":null,\"picture\":\"\",\"isAdmin\":true,\"state\":\"active\"},{\"id\":4,\"firstname\":\"Hugo\",\"lastname\":\"Martinez\",\"username\":\"hugomartinez\",\"email\":\"hugo.martinez@mail.com\",\"password\":null,\"picture\":\"\",\"isAdmin\":false,\"state\":\"active\"},{\"id\":5,\"firstname\":\"Carlos\",\"lastname\":\"Hernandez\",\"username\":\"carloshernandez\",\"email\":\"carlos.hernandez@mail.com\",\"password\":null,\"picture\":\"\",\"isAdmin\":false,\"state\":\"active\"},{\"id\":6,\"firstname\":\"Susanne\",\"lastname\":\"Lader\",\"username\":\"susannelader\",\"email\":\"susanne.lader@mail.com\",\"password\":null,\"picture\":\"\",\"isAdmin\":false,\"state\":\"active\"},{\"id\":7,\"firstname\":\"Jasmin\",\"lastname\":\"Rotovic\",\"username\":\"jasminrotovic\",\"email\":\"jasmin.rotovic@mail.com\",\"password\":null,\"picture\":\"\",\"isAdmin\":false,\"state\":\"blocked\"}]";

        ResponseEntity entity = new ResponseEntity(jsonList, HttpStatus.OK);

        Mockito.when(service.getUsers()).thenReturn(entity);

        mockMvc.perform(get("/api/users").accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(jsonList))
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

        ResponseEntity entity = new ResponseEntity(json, HttpStatus.OK);

        Mockito.when(service.getUser(1)).thenReturn(entity);

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

        ResponseEntity entity = new ResponseEntity(json,HttpStatus.OK);

        Mockito.when(service.updateUser(any(UserDto.class))).thenReturn(entity);

        mockMvc.perform(put("/api/users").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk());

    }

    @Test
    void deleteUser() {
    }


    @Test
    void deleteWithState() throws Exception {

          String string = "{ \"message\":\"deletedWithState\"}";

          ResponseEntity entity = new ResponseEntity(string, HttpStatus.OK);

          Mockito.when(service.deleteWithState(11)).thenReturn(entity);

        mockMvc.perform(put("/api/users/deleteWithState/11"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").exists());

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

        Mockito.when(service.changePassword(any(ChangePasswordDto.class))).thenReturn(entity);

        mockMvc.perform(put("/api/users/changePassword").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk());
    }
    @WithMockUser(roles = "ADMIN")
    @Test
    void toggleAdmin() throws Exception {
          String json = "{ \"message\":\"toggleAdmin\"}";
          ResponseEntity entity = new ResponseEntity(json,HttpStatus.OK);

          Mockito.when(service.toggleAdmin(10)).thenReturn(entity);

          mockMvc.perform(put("/api/users/toggleAdmin/10").contentType(MediaType.APPLICATION_JSON).content(json))
                  .andExpect(status().isOk())
                  .andExpect(MockMvcResultMatchers.jsonPath("$.message").exists());
    }

    @WithMockUser(roles = "ADMIN")
    @Test
    void toggleState() throws Exception {
        String json = "{ \"message\":\"toggleState\"}";

        ResponseEntity entity = new ResponseEntity(json,HttpStatus.OK);

        Mockito.when(service.toggleState(11)).thenReturn(entity);
        mockMvc.perform(put("/api/users/toggleState/11"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("toggleState"));

    }
    @Test
    void apiWrongRequest () throws Exception{

          ResponseEntity entity = new ResponseEntity(HttpStatus.NOT_FOUND);


          //user ->users
          mockMvc.perform(get("/api/user/10")).andExpect(status().isNotFound());

    }
}