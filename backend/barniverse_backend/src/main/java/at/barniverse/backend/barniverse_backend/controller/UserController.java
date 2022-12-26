package at.barniverse.backend.barniverse_backend.controller;

import at.barniverse.backend.barniverse_backend.dto.ChangePasswordDto;
import at.barniverse.backend.barniverse_backend.dto.UserDto;
import at.barniverse.backend.barniverse_backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
     * add a user to the database
     * @param userDto object sent from the client
     * @return response with corresponding status code and error message in case of failure
     */
    @PostMapping(path="/users")
    public ResponseEntity<Object> addUser(@RequestBody UserDto userDto) {
        return userService.addUser(userDto);
    }

    /**
     * get all saved users from the database
     * @return response with corresponding status code and loaded user dtos or error message in case of failure
     */
    @GetMapping(path="/users")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<Object> getUsers() {
        return userService.getUsers();
    }

    /**
     * get specific user from the database
     * @param id id of the specific user
     * @return response with corresponding status code and loaded user dto or error message in case of failure
     */
    @GetMapping(path="/users/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<Object> getUser(@PathVariable int id) {
        return userService.getUser(id);
    }

    //TODO: Safety alert! Users can be updated only with Id.
    /**
     * update specific user in the database
     * @param userDto object sent from the client (with id)
     * @return response with corresponding status code and error message in case of failure
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @PutMapping(path="/users")
    public ResponseEntity<Object> updateUser(@RequestBody UserDto userDto) {
        return userService.updateUser(userDto);
    }

    //TODO: Safety alert! Users can be deleted only with Id.
    /**
     * delete specific user from the database
     * @param id id of the specific user
     * @return response with corresponding status code and error message in case of failure
     */
    @DeleteMapping(path="/users/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable int id) {
        return userService.deleteUser(id);
    }

    /**
     * deactivate specific user
     * @param id id of the specific user
     * @return response with corresponding status code and error message in case of failure
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @PutMapping(path="/users/{id}")
    public ResponseEntity<Object> deactivateUser(@PathVariable int id) {
        return userService.deactivateUser(id);
    }

    /**
     * changing password of specific user
     * @param changePasswordDto change password dto sent from the client
     * @return response with corresponding status code and error message in case of failure
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @PutMapping(path="/users/changePassword")
    public ResponseEntity<Object> changePassword(@RequestBody ChangePasswordDto changePasswordDto) {
        return userService.changePassword(changePasswordDto);
    }

}
