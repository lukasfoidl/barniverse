package at.barniverse.backend.barniverse_backend.services;

import at.barniverse.backend.barniverse_backend.exception.BarniverseException;
import at.barniverse.backend.barniverse_backend.enums.AuctionState;
import at.barniverse.backend.barniverse_backend.enums.ProductState;
import at.barniverse.backend.barniverse_backend.enums.UserState;
import at.barniverse.backend.barniverse_backend.model.Auction;
import at.barniverse.backend.barniverse_backend.repository.AuctionRepository;
import at.barniverse.backend.barniverse_backend.repository.ProductRepository;
import at.barniverse.backend.barniverse_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    public List<String> validateEntitySpecificExtras(Auction auction) throws BarniverseException {
        List<String> errors = new ArrayList<>();
        boolean isPOST = auction.getId() == 0;

        try {
            errors = validateAuction(errors, auction, isPOST); // validate entity itself
            errors = validateUser(errors, auction, isPOST); // validate foreign key user
            errors = validateProduct(errors, auction, isPOST); // validate foreign key product
        } catch (Exception exception) {
            throw new BarniverseException(List.of(VALIDATION_ERROR), HttpStatus.INTERNAL_SERVER_ERROR, exception);
        }

        return errors;
    }

    private List<String> validateAuction(List<String> errors, Auction auction, boolean isPOST) {
        if (!isPOST) { // PUT (existence already checked in getEntity())
            // do not update inactive auction, on POST always set to active
            if (!auctionRepository.existsByIdAndState(auction.getId(), AuctionState.active)) {
                errors.add("Auction is not active!");
            }
            // prevent updating auction after start date (POST case secured with @Future annotation of startDate)
            if (!auctionRepository.existsByIdAndStartDateAfter(auction.getId(), LocalDateTime.now())) {
                errors.add("Auction already started, no changes possible!");
            }
        }
        return errors;
    }

    private List<String> validateUser(List<String> errors, Auction auction, boolean isPOST) {
        if (!userRepository.existsById(auction.getUser().getId())) {
            errors.add("User not found!");
        } else if (!userRepository.existsByIdAndState(auction.getUser().getId(), UserState.active)) {
            errors.add("User is not active!");
        }
        return errors;
    }

    private List<String> validateProduct(List<String> errors, Auction auction, boolean isPOST) {
        if (isPOST) {
            // only POST because product cannot be updated with PUT and product state is irrelevant in case of PUT
            if (!productRepository.existsById(auction.getProduct().getId())) {
                errors.add("Product not found!");
            } else if (!productRepository.existsByIdAndState(auction.getProduct().getId(), ProductState.active)) {
                errors.add("Product is not active!");
            }
        }
        return errors;
    }

    /**
     * validates if auction has not ended yet (end date reached)
     * @param auction entity which should be validated
     */
    public void validateTaskToggleAuction(Auction auction) throws BarniverseException {
        if (auction.getEndDate().compareTo(LocalDateTime.now()) < 0) {
            throw new BarniverseException(List.of("Auction already ended, state can not be updated!"), HttpStatus.FORBIDDEN, null);
        }
    }
}
