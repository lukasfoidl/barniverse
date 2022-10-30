package at.barniverse.backend.barniverse_backend.services;

import at.barniverse.backend.barniverse_backend.model.Auction;
import at.barniverse.backend.barniverse_backend.repository.ProductRepository;
import at.barniverse.backend.barniverse_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static at.barniverse.backend.barniverse_backend.configuration.Context.VALIDATION_ERROR;

// validation service which validates entity specific extras (like foreign keys, subentities, etc.)
@Service
public class AuctionValidationService extends ValidationService<Auction> {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<String> validateEntitySpecificExtras(Auction auction) {
        List<String> errors = new ArrayList<>();

        try {
            if (!userRepository.existsById(auction.getUser().getId())) {
                errors.add("User not found!");
            }
            if (!productRepository.existsById(auction.getProduct().getId())) {
                errors.add("Product not found!");
            }
        } catch (Exception exception) {
            return List.of(VALIDATION_ERROR);
        }

        return errors;
    }
}
