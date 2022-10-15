package at.barniverse.backend.barniverse_backend.controller;

import at.barniverse.backend.barniverse_backend.enums.UStatus;
import at.barniverse.backend.barniverse_backend.model.Product;
import at.barniverse.backend.barniverse_backend.model.User;
import at.barniverse.backend.barniverse_backend.repository.UserRepository;
import at.barniverse.backend.barniverse_backend.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/demo")
public class DemoController {

    @Autowired
    private ProductRepository productRepository;

    @PostMapping(path="/add")
    public @ResponseBody String addNewUser(
            @RequestParam String firstname,
            @RequestParam String lastname,
            @RequestParam String username,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String picture,
            @RequestParam UStatus status,
            @RequestParam boolean isAdmin
            ) {
        User user = new User();
        user.setFirstname(firstname);
        user.setLastname(lastname);
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        user.setPicture(picture);
        user.setStatus(status);
        user.setAdmin(isAdmin);
        //userRepository.save(user);
        return "Saved";
    }

    @GetMapping(path="/all")
    public @ResponseBody Iterable<Product> getAllProducts() {
        return productRepository.findAll();
    }
}
