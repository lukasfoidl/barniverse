package at.barniverse.backend.barniverse_backend.controller;

import at.barniverse.backend.barniverse_backend.enums.UStatus;
import at.barniverse.backend.barniverse_backend.model.*;
import at.barniverse.backend.barniverse_backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/demo")
public class DemoController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductImageRepository productImageRepository;
    @Autowired
    private AuctionRepository auctionRepository;
    @Autowired
    private OfferRepository offerRepository;

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

    @GetMapping(path="/users")
    public @ResponseBody Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping(path="/products")
    public @ResponseBody Iterable<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @GetMapping(path="/images")
    public @ResponseBody Iterable<ProductImage> getAllImages() {
        return productImageRepository.findAll();
    }

    @GetMapping(path="/auctions")
    public @ResponseBody Iterable<Auction> getAllAuctions() {
        return auctionRepository.findAll();
    }

    @GetMapping(path="/offers")
    public @ResponseBody Iterable<Offer> getAllOffers() {
        return offerRepository.findAll();
    }
}
