package at.barniverse.backend.barniverse_backend.services;

import at.barniverse.backend.barniverse_backend.enums.OfferState;
import at.barniverse.backend.barniverse_backend.model.Auction;
import at.barniverse.backend.barniverse_backend.model.Offer;
import at.barniverse.backend.barniverse_backend.model.Product;
import at.barniverse.backend.barniverse_backend.model.User;
import at.barniverse.backend.barniverse_backend.repository.AuctionRepository;
import at.barniverse.backend.barniverse_backend.repository.OfferRepository;
import at.barniverse.backend.barniverse_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static at.barniverse.backend.barniverse_backend.configuration.Context.VALIDATION_ERROR;

/**
 * validation service which validates offer specific extras
 */
@Service
public class OfferValidationService extends ValidationService<Offer> {

    @Autowired private UserRepository userRepository;
    @Autowired private AuctionRepository auctionRepository;
    @Autowired private OfferRepository offerRepository;

    /**
     * validates offer specific extras
     * @param offer entity which should be validated
     * @return error messages, empty if validation was successful
     */
    @Override
    public List<String> validateEntitySpecificExtras(Offer offer) {
        List<String> errors = new ArrayList<>();
        boolean isPOST = offer.getId() == 0;

        try {
            errors = validateOffer(errors, offer, isPOST); // validate entity itself
            errors = validateUser(errors, offer, isPOST); // validate foreign key user
            errors = validateAuction(errors, offer, isPOST); // validate foreign key auction
        } catch (Exception exception) {
            return List.of(VALIDATION_ERROR);
        }

        return errors;
    }

    private List<String> validateOffer(List<String> errors, Offer offer, boolean isPOST) throws Exception {
        if(!isPOST) {
            Offer dbOffer = offerRepository.findById(offer.getId()).get(); // existence already checked before validation
            // only update running auctions, on POST always running
            if (!dbOffer.IsRunning()) {
                errors.add("Offer not running! Only running offers can be changed!");
            }
        }
        return errors;
    }

    private List<String> validateUser(List<String> errors, Offer offer, boolean isPOST) throws Exception {
        Optional<User> user = userRepository.findById(offer.getUser().getId());
        if (user.isPresent()) {
            if (!user.get().isActive()) {
                errors.add("User is not active!");
            }
        } else {
            errors.add("User not found!");
        }
        return errors;
    }

    private List<String> validateAuction(List<String> errors, Offer offer, boolean isPOST) throws Exception {
        Optional<Auction> auction = auctionRepository.findById(offer.getAuction().getId());
        if (auction.isPresent()) {
            if (!auction.get().isActive()) {
                errors.add("Auction is not active!");
            }
            // prevent saving offers outside auction running time frame
            if (auction.get().getStartDate().compareTo(LocalDateTime.now()) > 0 || auction.get().getEndDate().compareTo(LocalDateTime.now()) < 0) {
                errors.add("Auction is not running, no offers can be placed!");
            }
        } else {
            errors.add("Auction not found!");
        }
        return errors;
    }
}
