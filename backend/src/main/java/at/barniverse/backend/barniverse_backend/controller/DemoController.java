package at.barniverse.backend.barniverse_backend.controller;

import at.barniverse.backend.barniverse_backend.model.*;
import at.barniverse.backend.barniverse_backend.repository.*;
import at.barniverse.backend.barniverse_backend.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

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

    @PostMapping(path="/addUser")
    public ResponseEntity<List<String>> addNewUser(@Valid User user, BindingResult bindingResult) {
        ResponseEntity<List<String>> response = ResponseService.createResponse(bindingResult, "User created successfully!");
        if (response.getStatusCode() == HttpStatus.OK) {
            userRepository.save(user);
        }
        return response;
    }

    @PostMapping(path="/addAuction")
    public ResponseEntity<List<String>> addNewAuction(@Valid Auction auction, BindingResult bindingResult) {
        ResponseEntity<List<String>> response = ResponseService.createResponse(bindingResult, "Auction created successfully!");
        if (response.getStatusCode() == HttpStatus.OK) {
            auctionRepository.save(auction);
        }
        return response;
    }

    @PostMapping(path="/addOffer")
    public ResponseEntity<List<String>> addNewOOffer(@Valid Offer offer, BindingResult bindingResult) {
        ResponseEntity<List<String>> response = ResponseService.createResponse(bindingResult, "Offer created successfully!");
        if (response.getStatusCode() == HttpStatus.OK) {
            offerRepository.save(offer);
        }
        return response;
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
