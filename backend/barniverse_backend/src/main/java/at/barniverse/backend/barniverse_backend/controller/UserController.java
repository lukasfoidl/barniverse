package at.barniverse.backend.barniverse_backend.controller;

import at.barniverse.backend.barniverse_backend.exception.BarniverseException;
import at.barniverse.backend.barniverse_backend.dto.ChangePasswordDto;
import at.barniverse.backend.barniverse_backend.dto.UserDto;
import at.barniverse.backend.barniverse_backend.enums.UserState;
import at.barniverse.backend.barniverse_backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static at.barniverse.backend.barniverse_backend.configuration.Context.CORS_ORIGINS;

/**
 * controller with basic CRUD routing for user related URLs
 */
@RestController
@CrossOrigin(origins = CORS_ORIGINS)
@RequestMapping(path = "/api")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * get all saved users from the database which do not have state deleted, <br>
     * reserved for role admin
     * @return response with corresponding status code and loaded user dtos
     * @throws BarniverseException in case of failure which includes error messages
     */
    @GetMapping(path="/users")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<Object> getUsers() throws BarniverseException {
        List<UserDto> results = userService.getUsers();
        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    /**
     * get specific user from the database, <br>
     * reserved for role user and admin
     * @param id id of the specific user
     * @return response with corresponding status code and loaded user dto
     * @throws BarniverseException in case of failure which includes error messages
     */
    @GetMapping(path="/users/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<Object> getUser(@PathVariable int id) throws BarniverseException {
        UserDto result = userService.getUser(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    //TODO: Safety alert! Users can be updated only with Id.
    /**
     * update specific user in the database, <br>
     * reserved for role user and admin
     * @param userDto object sent from the client (with id)
     * @return response with the corresponding status code and a json web token map
     * @throws BarniverseException in case of failure which includes error messages
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @PutMapping(path="/users")
    public ResponseEntity<Object> updateUser(@RequestBody UserDto userDto) throws BarniverseException {
        Map<String, String> tokenMap = userService.updateUser(userDto);
        return new ResponseEntity<>(tokenMap, HttpStatus.OK);
    }

    /**
     * deletes a user with state deleted, <br>
     * reserved for role user and admin
     * @param id id of the specific user
     * @return response with corresponding status code
     * @throws BarniverseException in case of failure which includes error messages
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @PutMapping(path="/users/{id}/deleteWithState")
    public ResponseEntity<Object> deleteWithState(@PathVariable int id) throws BarniverseException {
        userService.deleteWithState(id);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    /**
     * changing password of specific user, <br>
     * rserved for role user and admin
     * @param changePasswordDto change password dto sent from the client
     * @return response with corresponding status code
     * @throws BarniverseException in case of failure which includes error messages
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @PutMapping(path="/users/changePassword")
    public ResponseEntity<Object> changePassword(@RequestBody ChangePasswordDto changePasswordDto) throws BarniverseException {
        userService.changePassword(changePasswordDto);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    /**
     * toggles the admin value of a specific user (give admin rights or take admin rights), <br>
     * reserved for role admin
     * @param id id of the specific user
     * @return response with corresponding status code
     * @throws BarniverseException in case of failure which includes error messages
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PutMapping(path="/users/{id}/toggleAdmin")
    public ResponseEntity<Object> toggleAdmin(@PathVariable int id) throws BarniverseException {
        userService.toggleAdmin(id);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    /**
     * toggles the user state of a specific user (deactivate or activate), <br>
     * reserved for role admin
     * @param id id of the specific user
     * @return response with corresponding status code and set state
     * @throws BarniverseException in case of failure which includes error messages
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PutMapping(path="/users/{id}/toggleState")
    public ResponseEntity<Object> toggleState(@PathVariable int id) throws BarniverseException {
        UserState result = userService.toggleState(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
