package at.barniverse.backend.barniverse_backend.services;

import at.barniverse.backend.barniverse_backend.model.Offer;
import at.barniverse.backend.barniverse_backend.repository.AuctionRepository;
import at.barniverse.backend.barniverse_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static at.barniverse.backend.barniverse_backend.configuration.Context.VALIDATION_ERROR;

/**
 * validation service which validates offer specific extras
 */
@Service
public class OfferValidationService extends ValidationService<Offer> {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuctionRepository auctionRepository;

    /**
     * validates offer specific extras
     * @param offer entity which should be validated
     * @return error messages, empty if validation was successful
     */
    @Override
    public List<String> validateEntitySpecificExtras(Offer offer) {
        List<String> errors = new ArrayList<>();

        try {
            if (!userRepository.existsById(offer.getUser().getId())) {
                errors.add("User not found!");
            }
            if (!auctionRepository.existsById(offer.getAuction().getId())) {
                errors.add("Auction not found!");
            }
        } catch (Exception exception) {
            return List.of(VALIDATION_ERROR);
        }

        return errors;
    }
}
