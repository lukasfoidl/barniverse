package at.barniverse.backend.barniverse_backend.services;

import at.barniverse.backend.barniverse_backend.dto.OfferDto;
import at.barniverse.backend.barniverse_backend.enums.AuctionState;
import at.barniverse.backend.barniverse_backend.enums.OfferState;
import at.barniverse.backend.barniverse_backend.model.Auction;
import at.barniverse.backend.barniverse_backend.model.Offer;
import at.barniverse.backend.barniverse_backend.repository.OfferRepository;
import at.barniverse.backend.barniverse_backend.transformer.OfferTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static at.barniverse.backend.barniverse_backend.configuration.Context.DATABASE_ERROR;

/**
 * service with offer related functionality
 */
@Service
public class OfferService extends BaseService {

    @Autowired
    private OfferRepository offerRepository;

    @Autowired
    private OfferTransformer offerTransformer;

    @Autowired
    private OfferValidationService offerValidationService;

    /**
     * add an offer to the database
     * @param offerDto dto which should be saved
     * @return response with corresponding status code and error message in case of failure
     */
    public ResponseEntity<Object> addOffer(OfferDto offerDto) {
        offerDto.setState(OfferState.running);
        return addEntity(offerRepository, offerTransformer, offerValidationService, offerDto);
    }

    /**
     *get all saved offers from the database
     * @return response with corresponding status code and loaded offer dtos or error message in case of failure
     */
    public ResponseEntity<Object> getOffers() {
        return getEntities(offerRepository, offerTransformer);
    }

    /**
     * get specific offer from the database
     * @param id id of the specific offer
     * @return response with corresponding status code and loaded offer dto or error message in case of failure
     */
    public ResponseEntity<Object> getOffer(int id) {
        return getEntityAsDto(offerRepository, offerTransformer, id);
    }

    /**
     * get all saved offers from a specific auction
     * @param id id of the specific auction
     * @return response with corresponding status code and loaded offer dtos or error message in case of failure
     */
    public ResponseEntity<Object> getOffersFromAuction(int id) {
        List<Offer> entities;
        try {
            entities = offerRepository.findByAuction_Id(id);
        } catch (Exception exception) {
            return new ResponseEntity<>(DATABASE_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        List<OfferDto> dtos = new ArrayList<>();
        entities.forEach(entity -> {
            dtos.add(offerTransformer.convertToDto(entity));
        });
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    /**
     * get all saved offers from a specific user
     * @param id id of the specific user
     * @return response with corresponding status code and loaded offer dtos or error message in case of failure
     */
    public ResponseEntity<Object> getOffersFromUser(int id) {
        List<Offer> entities;
        try {
            entities = offerRepository.findByUser_Id(id);
        } catch (Exception exception) {
            return new ResponseEntity<>(DATABASE_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        List<OfferDto> dtos = new ArrayList<>();
        entities.forEach(entity -> {
            dtos.add(offerTransformer.convertToDto(entity));
        });
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    /**
     * update specific offer in the database
     * @param offerDto dto which should be updated (with id)
     * @return response with corresponding status code and error message in case of failure
     */
    public ResponseEntity<Object> updateOffer(OfferDto offerDto) {
        return updateEntity(offerRepository, offerTransformer, offerValidationService, offerDto);
    }

    /**
     * delete specific offer from the database
     * @param id id of the specific offer
     * @return response with corresponding status code and error message in case of failure
     */
    public ResponseEntity<Object> deleteOffer(int id) {
        return deleteEntity(offerRepository, id);
    }

    /**
     * update specific offer state to accept, <br>
     * update offer state to rejected for all other offers of the accepted offers auction
     * @param id id of the specific offer
     * @return response with corresponding status code and error message in case of failure
     */
    public ResponseEntity<Object> acceptOffer(int id) {
        ResponseEntity<Object> response = getEntity(offerRepository, id);
        if (response.getStatusCode() != HttpStatus.OK) {
            return response;
        }
        Offer offer = (Offer) response.getBody();

        List<Offer> offers;
        try {
            offers = offerRepository.findByAuction_Id(offer.getAuction().getId()); // existence check in getEntity()
            for (Offer item : offers) {
                if (item.getId() == id) {
                    item.setState(OfferState.accepted);
                } else {
                    item.setState(OfferState.rejected);
                }
            }
            offerRepository.saveAll(offers);
        } catch (Exception exception) {
            return new ResponseEntity<>(DATABASE_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
