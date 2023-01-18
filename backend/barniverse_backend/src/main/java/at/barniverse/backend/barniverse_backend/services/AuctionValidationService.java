package at.barniverse.backend.barniverse_backend.services;

import at.barniverse.backend.barniverse_backend.model.Auction;
import at.barniverse.backend.barniverse_backend.model.Product;
import at.barniverse.backend.barniverse_backend.model.User;
import at.barniverse.backend.barniverse_backend.repository.AuctionRepository;
import at.barniverse.backend.barniverse_backend.repository.ProductRepository;
import at.barniverse.backend.barniverse_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static at.barniverse.backend.barniverse_backend.configuration.Context.VALIDATION_ERROR;

/**
 * validation service which validates auction specific extras
 */
@Service
public class AuctionValidationService extends ValidationService<Auction> {

    @Autowired private UserRepository userRepository;
    @Autowired private ProductRepository productRepository;
    @Autowired private AuctionRepository auctionRepository;

    /**
     * validates auction specific extras
     * @param auction entity which should be validated
     * @return error messages, empty if validation was successful
     */
    @Override
    public List<String> validateEntitySpecificExtras(Auction auction) {
        List<String> errors = new ArrayList<>();
        boolean isPOST = auction.getId() == 0;

        try {
            errors = validateAuction(errors, auction, isPOST); // validate entity itself
            errors = validateUser(errors, auction, isPOST); // validate foreign key user
            errors = validateProduct(errors, auction, isPOST); // validate foreign key product
        } catch (Exception exception) {
            return List.of(VALIDATION_ERROR);
        }

        return errors;
    }

    private List<String> validateAuction(List<String> errors, Auction auction, boolean isPOST) throws Exception {
        if (!isPOST) { // PUT
            Auction dbAuction = auctionRepository.findById(auction.getId()).get(); // existence already checked before validation
            // do not update inactive auction, on POST always set to active
            if (!dbAuction.isActive()) {
                errors.add("Auction is not active!");
            }
            // prevent updating auction after start date (POST case secured with @Future annotation of startDate)
            if (dbAuction.getStartDate().compareTo(LocalDateTime.now()) <= 0) {
                errors.add("Auction already started, no changes possible!");
            }
        }
        return errors;
    }

    private List<String> validateUser(List<String> errors, Auction auction, boolean isPOST) throws Exception {
        Optional<User> user = userRepository.findById(auction.getUser().getId());
        if (user.isPresent()) {
            if (!user.get().isActive()) {
                errors.add("User is not active!");
            }
        } else {
            errors.add("User not found!");
        }
        return errors;
    }

    private List<String> validateProduct(List<String> errors, Auction auction, boolean isPOST) throws Exception {
        if (isPOST) {
            // only POST because product cannot be updated with PUT and product state is irrelevant in case of PUT
            Optional<Product> product = productRepository.findById(auction.getProduct().getId());
            if (product.isPresent()) {
                if (!product.get().isActive()) {
                    errors.add("Product is not active!");
                }
            } else {
                errors.add("Product not found!");
            }
        }
        return errors;
    }
}
