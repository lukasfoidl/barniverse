package at.barniverse.backend.barniverse_backend.services;

import at.barniverse.backend.barniverse_backend.configuration.Context;
import at.barniverse.backend.barniverse_backend.dto.AuthDto;
import at.barniverse.backend.barniverse_backend.dto.ChangePasswordDto;
import at.barniverse.backend.barniverse_backend.dto.UserDto;
import at.barniverse.backend.barniverse_backend.enums.RoleConverter;
import at.barniverse.backend.barniverse_backend.enums.UserState;
import at.barniverse.backend.barniverse_backend.exception.BarniverseException;
import at.barniverse.backend.barniverse_backend.model.User;
import at.barniverse.backend.barniverse_backend.repository.UserRepository;
import at.barniverse.backend.barniverse_backend.security.JWTUtil;
import at.barniverse.backend.barniverse_backend.transformer.UserTransformer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {



    @Mock
    UserRepository userRepository;


    @Mock
    UserTransformer userTransformer;

    @Mock
    UserValidationService userValidationService;

    @Mock
    JWTUtil jwtUtil;

    @Mock
    PasswordEncoder passwordEncoder;

    @InjectMocks
    UserService userService;

    User user;

    UserDto userDto;

    @BeforeEach
    void setup(){
       //userRepository = Mockito.mock(UserRepository.class);
       //userService = new UserService(userRepository, userTransformer, userValidationService, passwordEncoder, jwtUtil);

        userDto = new UserDto();
        userDto.setId(1);
        userDto.setFirstname("John1");
        userDto.setLastname("Doe1");
        userDto.setUsername("JonnyDoe1231");
        userDto.setEmail("jonnydoe@test1.at");
        userDto.setPassword("dgsdfg456dzhhmnnlkklghknfghndhgmztuitjl5");
        userDto.setState(UserState.active);
        userDto.setPicture("test1.png");

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
    void getUsers() throws Exception {

        List<User> list = new ArrayList<User>();
        User user1 = new User();
        user1.setId(1);
        user1.setFirstname("John1");
        user1.setLastname("Doe1");
        user1.setUsername("JonnyDoe1231");
        user1.setEmail("jonnydoe@test1.at");
        user1.setPassword("dgsdfg456dzhhmnnlkklghknfghndhgmztuitjl5");
        user1.setState(UserState.active);
        user1.setPicture("test1.png");

        UserDto userDto1 = new UserDto();
        userDto1.setId(1);
        userDto1.setFirstname("John1");
        userDto1.setLastname("Doe1");
        userDto1.setUsername("JonnyDoe1231");
        userDto1.setEmail("jonnydoe@test1.at");
        userDto1.setPassword("dgsdfg456dzhhmnnlkklghknfghndhgmztuitjl5");
        userDto1.setState(UserState.active);
        userDto1.setPicture("test1.png");

        User user2 = new User();
        user2.setId(1);
        user2.setFirstname("John2");
        user2.setLastname("Doe2");
        user2.setUsername("JonnyDoe1232");
        user2.setEmail("jonnydoe@test2.at");
        user2.setPassword("dgsdfg456dzhhmnnlkklghknfghndhgmztuitjl5");
        user2.setState(UserState.active);
        user2.setPicture("test2.png");

        UserDto userDto2 = new UserDto();
        userDto2.setId(1);
        userDto2.setFirstname("John2");
        userDto2.setLastname("Doe2");
        userDto2.setUsername("JonnyDoe1232");
        userDto2.setEmail("jonnydoe@test2.at");
        userDto2.setPassword("dgsdfg456dzhhmnnlkklghknfghndhgmztuitjl5");
        userDto2.setState(UserState.active);
        userDto2.setPicture("test2.png");

        User user3 = new User();
        user3.setId(1);
        user3.setFirstname("John3");
        user3.setLastname("Doe3");
        user3.setUsername("JonnyDoe1233");
        user3.setEmail("jonnydoe@test3.at");
        user3.setPassword("dgsdfg456dzhhmnnlkklghknfghndhgmztuitjl5");
        user3.setState(UserState.active);
        user3.setPicture("test3.png");

        UserDto userDto3 = new UserDto();
        userDto3.setId(1);
        userDto3.setFirstname("John3");
        userDto3.setLastname("Doe3");
        userDto3.setUsername("JonnyDoe1233");
        userDto3.setEmail("jonnydoe@test3.at");
        userDto3.setPassword("dgsdfg456dzhhmnnlkklghknfghndhgmztuitjl5");
        userDto3.setState(UserState.active);
        userDto3.setPicture("test3.png");

        list.add(user1);
        list.add(user2);
        list.add(user3);

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(list);

        ResponseEntity<Object> entityExpected = new ResponseEntity(json, HttpStatus.OK);


        //given
        given(userRepository.findAllByStateOrState(UserState.active, UserState.blocked)).willReturn(list);
        given(userTransformer.convertToDto(user1)).willReturn(userDto1);
        given(userTransformer.convertToDto(user2)).willReturn(userDto2);
        given(userTransformer.convertToDto(user3)).willReturn(userDto3);

        //when
        List<UserDto> listGet =  userService.getUsers();

        System.out.println(list.get(0).getFirstname());
        System.out.println(listGet.get(0).getFirstname());

        //then

        assertEquals(list.get(0).getFirstname(),listGet.get(0).getFirstname());
        assertEquals(list.get(0).getEmail(),listGet.get(0).getEmail());
        assertEquals(list.get(0).getUsername(),listGet.get(0).getUsername());
    }

    @Test
    void getUserByIdCorrectly() throws Exception {


        Optional<User> optional = Optional.of(user);
        //given
        given(userRepository.findById(user.getId())).willReturn(optional);
        given(userTransformer.convertToDto(user)).willReturn(userDto);

        //when
        //test failed in base service get EntityasDto and sends the reference instead of the values of the dto --> added object mapper to getEntityAsDto
        UserDto newUserDto = userService.getUser( 1);

        System.out.println(user.getId());
        System.out.println(newUserDto.getId());
        System.out.println(user.getEmail());
        System.out.println(newUserDto.getEmail());
        System.out.println(user.getUsername());
        System.out.println(newUserDto.getUsername());

        //then
        assertEquals(user.getId(), newUserDto.getId());
        assertEquals(user.getEmail(), newUserDto.getEmail());
        assertEquals(user.getUsername(), newUserDto.getUsername());



    }



    @Test
    void updateUser() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(userDto);

        ResponseEntity entityExpected = new ResponseEntity(null, HttpStatus.OK);

        Optional<User> optional = Optional.of(user);

        User user2 = new User();
        user2.setId(1);
        user2.setFirstname("John2");
        user2.setLastname("Doe2");
        user2.setUsername("JonnyDoe1232");
        user2.setEmail("jonnydoe@test2.at");
        user2.setPassword("asdfretewr654645645dfftasdfuitjl5");
        user2.setState(UserState.active);
        user2.setPicture("test2.png");

        User userUpdated = new User();
        userUpdated.setId(1);
        userUpdated.setFirstname("John2");
        userUpdated.setLastname("Doe2");
        userUpdated.setUsername("JonnyDoe1232");
        userUpdated.setEmail("jonnydoe@test2.at");
        userUpdated.setPassword("asdfretewr654645645dfftasdfuitjl5");
        userUpdated.setState(UserState.active);
        userUpdated.setPicture("test2.png");

        Optional<User> optional2 = Optional.of(user2);

        UserDto userDto2 = new UserDto();
        userDto2.setId(1);
        userDto2.setFirstname("John2");
        userDto2.setLastname("Doe2");
        userDto2.setUsername("JonnyDoe1232");
        userDto2.setEmail("jonnydoe@test2.at");
        userDto2.setPassword(null);
        userDto2.setState(UserState.active);
        userDto2.setPicture("test2.png");

         AuthDto authDto = new AuthDto();
         authDto.setUsername(user2.getEmail());
         authDto.setUsername(user2.getUsername());
         authDto.setRole(user2.getState().toString());
         authDto.setUuid(Integer.toString(user2.getId()));

        //given
        given(userRepository.findById(user.getId())).willReturn(optional);
        given(userTransformer.convertToEntity(userDto2)).willReturn(user2);
        given(userTransformer.repairEntity(any(User.class), any(User.class))).willReturn(user);
        given(userRepository.findById(userUpdated.getId())).willReturn(optional2);
        given(jwtUtil.getToken(any(AuthDto.class))).willReturn(Collections.singletonMap("jwt-token", "token"));

        //when
        //test failed in base service get Entitys Dto and sends the reference instead of the values of the dto --> added object mapper to getEntityAsDto
        System.out.println(userService.updateUser(userDto2));
        System.out.println(Collections.singletonMap("jwt-token", "token"));


        //then
        assertEquals(userService.updateUser(userDto2),Collections.singletonMap("jwt-token", "token"));


    }

    @Test
    void deleteUser() throws Exception{


    }

    @Test
    void deleteWithState() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(user);

        Optional<User> optional = Optional.of(user);

        //given
        given(userRepository.findById(user.getId())).willReturn(optional);
        given(userRepository.save(user)).willReturn(user);

        //when
        userService.deleteWithState(user.getId());

        //then
        verify(userRepository, times(1)).findById(user.getId());
        verify(userRepository, times(1)).save(user);

    }

    @Test
    void changePassword() throws BarniverseException {



        String encodePw = "Encoded Password";

        ChangePasswordDto dto = new ChangePasswordDto();
        dto.setPassword(user.getPassword());
        dto.setId(user.getId());

        Optional optionalUser = Optional.of(user);

        ResponseEntity expected = new ResponseEntity(null, HttpStatus.OK);

        //given
        given(userRepository.findById(user.getId())).willReturn(optionalUser);
        given(passwordEncoder.encode(dto.getPassword())).willReturn(encodePw);

        //when
        userService.changePassword(dto);

        System.out.println(expected);
        //then
        verify(userRepository,times(1)).save(user);




    }

    @Test
    void toggleAdmin() throws BarniverseException {

        ChangePasswordDto dto = new ChangePasswordDto();
        dto.setPassword(user.getPassword());
        dto.setId(user.getId());

        Optional optionalUser = Optional.of(user);

        //given
        given(userRepository.findById(user.getId())).willReturn(optionalUser);

        //when
        userService.toggleAdmin(1);

        //then
        verify(userRepository, times(1)).findById(user.getId());
        verify(userRepository, times(1)).save(user);

    }

    @Test
    void toggleState() throws BarniverseException {
        //user state = active in the beginning

        Optional optionalUser = Optional.of(user);

        //given
        given(userRepository.findById(user.getId())).willReturn(optionalUser);
        given(userRepository.save(user)).willReturn(user);

        //when
        UserState state = userService.toggleState(1);

        System.out.println(user.getState());
        System.out.println(state);

        //then
        assertEquals(user.getState(), state);

    }


    @Test
    void toggleStateOfBlockedUser() throws BarniverseException {

        User userDeleted = new User();
        userDeleted.setId(1);
        userDeleted.setFirstname("John1");
        userDeleted.setLastname("Doe1");
        userDeleted.setUsername("JonnyDoe1231");
        userDeleted.setEmail("jonnydoe@test1.at");
        userDeleted.setPassword("dgsdfg456dzhhmnnlkklghknfghndhgmztuitjl5");
        userDeleted.setState(UserState.deleted);
        userDeleted.setPicture("test1.png");

        Optional optionalUser = Optional.of(userDeleted);

        //given
        given(userRepository.findById(userDeleted.getId())).willReturn(optionalUser);

        String stateString = "";
        List<String> excepetion = new ArrayList<>();
        BarniverseException ex = new BarniverseException(List.of("User is deleted and cannot be changed!"), HttpStatus.FORBIDDEN, null);
        //when
        try{
            stateString = userService.toggleState(1).toString();
        }catch (BarniverseException e){
            System.out.println(e);
            excepetion = e.getErrorMessages();
        }

        System.out.println(ex.getErrorMessages());
        System.out.println(excepetion);

        //then
        assertEquals(ex.getErrorMessages(), excepetion);
        assertEquals("", stateString.toString());
    }
}