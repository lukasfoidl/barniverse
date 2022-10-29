package at.barniverse.backend.barniverse_backend.transformer;

import at.barniverse.backend.barniverse_backend.dto.UserDto;
import at.barniverse.backend.barniverse_backend.model.User;
import org.springframework.stereotype.Component;

// user transformer for convertions between entity <-> dto
@Component
public class UserTransformer implements ITransformer<User, UserDto> {

    public UserDto convertToDto(User user) {
        UserDto userDto = new UserDto();

        userDto.setId(user.getId());
        userDto.setFirstname(user.getFirstname());
        userDto.setLastname(user.getLastname());
        userDto.setUsername(user.getUsername());
        userDto.setEmail(user.getEmail());
        // password should not be sent to the client
        userDto.setPicture(user.getPicture());
        userDto.setAdmin(user.isAdmin());
        userDto.setStatus(user.getStatus());

        return userDto;
    }

    public User convertToEntity(UserDto userDto) {
        User user = new User();

        user.setId(userDto.getId());
        user.setFirstname(userDto.getFirstname());
        user.setLastname(userDto.getLastname());
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setPicture(userDto.getPicture());
        user.setAdmin(userDto.isAdmin());
        user.setStatus(userDto.getStatus());

        return user;
    }
}
