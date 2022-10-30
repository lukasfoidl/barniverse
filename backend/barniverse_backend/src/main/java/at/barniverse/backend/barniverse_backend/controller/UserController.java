package at.barniverse.backend.barniverse_backend.controller;

import at.barniverse.backend.barniverse_backend.dto.UserDto;
import at.barniverse.backend.barniverse_backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// user controller with basic CRUD routing
@RestController
@RequestMapping(path = "/api")
public class UserController {

    @Autowired
    private UserService userService;

    // create new user
    @PostMapping(path="/users")
    public ResponseEntity<Object> addUser(@RequestBody UserDto userDto) {
        return userService.addUser(userDto);
    }

    // get all users
    @GetMapping(path="/users")
    public ResponseEntity<Object> getUsers() {
        return userService.getUsers();
    }

    // get specific users
    @GetMapping(path="/users/{id}")
    public ResponseEntity<Object> getUser(@PathVariable int id) {
        return userService.getUser(id);
    }

    // update specific user
    //TODO: Safety alert! Users can be updated only with Id.
    @PutMapping(path="/users")
    public ResponseEntity<Object> updateUser(@RequestBody UserDto userDto) {
        return userService.updateUser(userDto);
    }

    // delete specific user
    //TODO: Safety alert! Users can be deleted only with Id.
    @DeleteMapping(path="/users/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable int id) {
        return userService.deleteUser(id);
    }

}
