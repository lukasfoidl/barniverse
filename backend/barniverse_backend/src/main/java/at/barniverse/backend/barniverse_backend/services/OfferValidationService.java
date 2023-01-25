package at.barniverse.backend.barniverse_backend.services;

import at.barniverse.backend.barniverse_backend.exception.BarniverseException;
import at.barniverse.backend.barniverse_backend.enums.AuctionState;
import at.barniverse.backend.barniverse_backend.enums.OfferState;
import at.barniverse.backend.barniverse_backend.enums.UserState;
import at.barniverse.backend.barniverse_backend.model.Offer;
import at.barniverse.backend.barniverse_backend.repository.AuctionRepository;
import at.barniverse.backend.barniverse_backend.repository.OfferRepository;
import at.barniverse.backend.barniverse_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static at.barniverse.backend.barniverse_backend.configuration.Context.VALIDATION_ERROR;

/**
 * validation service with offer validation functionality
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
     * @throws BarniverseException in case of failure which includes error messages
     */
    @Override
    public List<String> validateEntitySpecificExtras(Offer offer) throws BarniverseException {
        List<String> errors = new ArrayList<>();
        boolean isPOST = offer.getId() == 0;

        try {
            errors = validateOffer(errors, offer, isPOST); // validate entity itself
            errors = validateUser(errors, offer, isPOST); // validate foreign key user
            errors = validateAuction(errors, offer, isPOST); // validate foreign key auction
        } catch (Exception exception) {
            throw new BarniverseException(List.of(VALIDATION_ERROR), HttpStatus.INTERNAL_SERVER_ERROR, exception);
        }

        return errors;
    }

    /**
     * extension method which validates offer itself
     * @param errors messages of errors which already arisen
     * @param offer offer which should be validated
     * @param isPOST true if validation happens in context of a POST request, otherwise false
     * @return messages of already arisen errors as well as new error messages
     */
    private List<String> validateOffer(List<String> errors, Offer offer, boolean isPOST) {
        if (!isPOST) { // PUT (existence already checked in getEntity())
            // only update running offers, on POST always running
            if (!offerRepository.existsByIdAndState(offer.getId(), OfferState.running)) {
                errors.add("Offer not running! Only running offers can be changed!");
            }
        }
        return errors;
    }

    /**
     * extension method which validates user property of an offer
     * @param errors messages of errors which already arisen
     * @param offer offer of which the user should be validated
     * @param isPOST true if validation happens in context of a POST request, otherwise false
     * @return messages of already arisen errors as well as new error messages
     */
    private List<String> validateUser(List<String> errors, Offer offer, boolean isPOST) {
        if (!userRepository.existsById(offer.getUser().getId())) {
            errors.add("User not found!");
        } else if (!userRepository.existsByIdAndState(offer.getUser().getId(), UserState.active)) {
            errors.add("User is not active!");
        }
        return errors;
    }

    /**
     * extension method which validates auction property of an offer
     * @param errors messages of errors which already arisen
     * @param offer offer of which the auction should be validated
     * @param isPOST true if validation happens in context of a POST request, otherwise false
     * @return messages of already arisen errors as well as new error messages
     */
    private List<String> validateAuction(List<String> errors, Offer offer, boolean isPOST) {
        if (!auctionRepository.existsById(offer.getAuction().getId())) {
            errors.add("Auction not found!");
        } else {
            if (!auctionRepository.existsByIdAndState(offer.getAuction().getId(), AuctionState.active)) {
                errors.add("Auction is not active!");
            }
            // prevent saving offers outside auction running time frame
            if (!auctionRepository.existsByIdAndStartDateBeforeAndEndDateAfter(offer.getAuction().getId(), LocalDateTime.now(), LocalDateTime.now())) {
                errors.add("Auction is not running, no offers can be placed!");
            }
        }
        return errors;
    }
}
