package at.barniverse.backend.barniverse_backend.transformer;

import at.barniverse.backend.barniverse_backend.dto.UserDto;
import at.barniverse.backend.barniverse_backend.model.User;
import org.springframework.stereotype.Component;

// user transformer for convertions between entity <-> dto
@Component
public class UserTransformer implements ITransformer<User, UserDto> {

    @Override
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

    @Override
    public User convertToEntity(UserDto userDto) {
        User user = new User();

        // id gets set from database
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

    // repair entity in case of update (PUT)
    @Override
    public User repairEntity(User user, User dbUser) {
        user.setId(dbUser.getId()); // set id to update existing entity
        user.setPassword(dbUser.getPassword()); // password can not be updated with standard user update (PUT)
        return user;
    }
}
