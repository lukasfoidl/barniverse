package at.barniverse.backend.barniverse_backend.transformer;

import at.barniverse.backend.barniverse_backend.dto.UserDto;
import at.barniverse.backend.barniverse_backend.model.User;
import org.springframework.stereotype.Component;

/**
 * transforms user entities and dtos
 */
@Component
public class UserTransformer implements ITransformer<User, UserDto> {

    /**
     * transforms user entity to user dto, <br>
     * password property does NOT get transformed from entity to dto, because password should not be sent to the client
     * @param user entity, which should be transformed
     * @return user dto
     */
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
        userDto.setIsAdmin(user.getIsAdmin());
        userDto.setState(user.getState());

        return userDto;
    }

    /**
     * transforms user dto to user entity, <br>
     * id property does NOT get transformed from dto to entity, because id gets set from the database automatically
     * @param userDto dto which should be transformed
     * @return user entity
     */
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
        user.setIsAdmin(userDto.getIsAdmin());
        user.setState(userDto.getState());

        return user;
    }

    /**
     * repairs user entity after transformation in case of update (PUT), <br>
     * id gets set to update entity, <br>
     * password property gets set to password property from database, because the password can not be updated with standard user update (PUT), <br>
     * isAdmin property gets set to isAdmin property from database, because the admin characteristic can not be updated with standard user update (PUT)
     * @param user entity which needs to be repaired
     * @param dbUser entity with the missing data
     * @return repaired entity
     */
    @Override
    public User repairEntity(User user, User dbUser) {
        user.setId(dbUser.getId()); // set id to update existing entity
        user.setPassword(dbUser.getPassword()); // password can not be updated with standard user update (PUT)
        user.setIsAdmin(dbUser.getIsAdmin()); // is admin can not be updated with standard user update (PUT)
        return user;
    }
}
