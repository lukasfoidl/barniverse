package at.barniverse.backend.barniverse_backend.transformer;

import at.barniverse.backend.barniverse_backend.dto.UserDto;
import at.barniverse.backend.barniverse_backend.enums.UserState;
import at.barniverse.backend.barniverse_backend.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserTransformerTests {

    @InjectMocks
    private UserTransformer userTransformer;

    @Test
    public void convertToDtoTest() {
        // given
        int userId = 1;
        String firstname = "Maximilian";
        String lastname = "Mustermann";
        String username = "maxmustermann";
        String email = "max.mustermann@mail.com";
        String passwordPlain = "testtest";
        String passwordHash = "$2a$10$VCK9hiTv.NKG7tXSXc4DLO5h4iJ00ZjjJQJdfZCH08WrKXxr5chwC";
        String picture = "Testdirectory/Testpicture.jpg";
        boolean isAdmin = false;
        UserState state = UserState.active;

        User user = new User();
        user.setId(userId);
        user.setFirstname(firstname);
        user.setLastname(lastname);
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordHash);
        user.setPicture(picture);
        user.setIsAdmin(isAdmin);
        user.setState(state);

        UserDto expectedUserDto = new UserDto();
        expectedUserDto.setId(userId);
        expectedUserDto.setFirstname(firstname);
        expectedUserDto.setLastname(lastname);
        expectedUserDto.setUsername(username);
        expectedUserDto.setEmail(email);
        expectedUserDto.setPassword(null); // password should not be sent to the client
        expectedUserDto.setPicture(picture);
        expectedUserDto.setIsAdmin(isAdmin);
        expectedUserDto.setState(state);

        // when
        UserDto actualUserDto = userTransformer.convertToDto(user);

        // assert
        Assertions.assertEquals(expectedUserDto.getId(), actualUserDto.getId());
        Assertions.assertEquals(expectedUserDto.getFirstname(), actualUserDto.getFirstname());
        Assertions.assertEquals(expectedUserDto.getLastname(), actualUserDto.getLastname());
        Assertions.assertEquals(expectedUserDto.getUsername(), actualUserDto.getUsername());
        Assertions.assertEquals(expectedUserDto.getEmail(), actualUserDto.getEmail());
        Assertions.assertEquals(expectedUserDto.getPassword(), actualUserDto.getPassword());
        Assertions.assertEquals(expectedUserDto.getPicture(), actualUserDto.getPicture());
        Assertions.assertEquals(expectedUserDto.getIsAdmin(), actualUserDto.getIsAdmin());
        Assertions.assertEquals(expectedUserDto.getState(), actualUserDto.getState());
    }

    @Test
    public void convertToEntityTest() {
        // given
        int userId = 1;
        String firstname = "Maximilian";
        String lastname = "Mustermann";
        String username = "maxmustermann";
        String email = "max.mustermann@mail.com";
        String passwordPlain = "testtest";
        String passwordHash = "$2a$10$VCK9hiTv.NKG7tXSXc4DLO5h4iJ00ZjjJQJdfZCH08WrKXxr5chwC";
        String picture = "Testdirectory/Testpicture.jpg";
        boolean isAdmin = false;
        UserState state = UserState.active;

        UserDto userDto = new UserDto();
        userDto.setId(userId);
        userDto.setFirstname(firstname);
        userDto.setLastname(lastname);
        userDto.setUsername(username);
        userDto.setEmail(email);
        userDto.setPassword(passwordHash);
        userDto.setPicture(picture);
        userDto.setIsAdmin(isAdmin);
        userDto.setState(state);

        User expectedUser = new User();
        expectedUser.setId(0); // id gets set from database
        expectedUser.setFirstname(firstname);
        expectedUser.setLastname(lastname);
        expectedUser.setUsername(username);
        expectedUser.setEmail(email);
        expectedUser.setPassword(passwordHash);
        expectedUser.setPicture(picture);
        expectedUser.setIsAdmin(isAdmin);
        expectedUser.setState(state);

        // when
        User actualUser = userTransformer.convertToEntity(userDto);

        // assert
        Assertions.assertEquals(expectedUser.getId(), actualUser.getId());
        Assertions.assertEquals(expectedUser.getFirstname(), actualUser.getFirstname());
        Assertions.assertEquals(expectedUser.getLastname(), actualUser.getLastname());
        Assertions.assertEquals(expectedUser.getUsername(), actualUser.getUsername());
        Assertions.assertEquals(expectedUser.getEmail(), actualUser.getEmail());
        Assertions.assertEquals(expectedUser.getPassword(), actualUser.getPassword());
        Assertions.assertEquals(expectedUser.getPicture(), actualUser.getPicture());
        Assertions.assertEquals(expectedUser.getIsAdmin(), actualUser.getIsAdmin());
        Assertions.assertEquals(expectedUser.getState(), actualUser.getState());
    }

    @Test
    public void repairEntityTest() {
        // given
        int userId = 1;
        String firstname = "Maximilian";
        String lastname = "Mustermann";
        String username = "maxmustermann";
        String email = "max.mustermann@mail.com";
        String passwordPlain = "testtest";
        String passwordHash = "$2a$10$VCK9hiTv.NKG7tXSXc4DLO5h4iJ00ZjjJQJdfZCH08WrKXxr5chwC";
        String picture = "Testdirectory/Testpicture.jpg";
        boolean isAdmin = false;
        UserState state = UserState.active;

        User user = new User();
        user.setId(99);
        user.setFirstname(firstname);
        user.setLastname(lastname);
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordPlain);
        user.setPicture(picture);
        user.setIsAdmin(true);
        user.setState(UserState.blocked);

        User dbUser = new User();
        dbUser.setId(userId);
        dbUser.setFirstname(firstname);
        dbUser.setLastname(lastname);
        dbUser.setUsername(username);
        dbUser.setEmail(email);
        dbUser.setPassword(passwordHash);
        dbUser.setPicture(picture);
        dbUser.setIsAdmin(isAdmin);
        dbUser.setState(state);

        // when
        User actualUser = userTransformer.repairEntity(user, dbUser);

        // assert
        Assertions.assertEquals(dbUser.getId(), actualUser.getId());
        Assertions.assertEquals(dbUser.getFirstname(), actualUser.getFirstname());
        Assertions.assertEquals(dbUser.getLastname(), actualUser.getLastname());
        Assertions.assertEquals(dbUser.getUsername(), actualUser.getUsername());
        Assertions.assertEquals(dbUser.getEmail(), actualUser.getEmail());
        Assertions.assertEquals(dbUser.getPassword(), actualUser.getPassword());
        Assertions.assertEquals(dbUser.getPicture(), actualUser.getPicture());
        Assertions.assertEquals(dbUser.getIsAdmin(), actualUser.getIsAdmin());
        Assertions.assertEquals(dbUser.getState(), actualUser.getState());
    }

}