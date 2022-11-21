package at.barniverse.backend.barniverse_backend.services;

import at.barniverse.backend.barniverse_backend.dto.OfferDto;
import at.barniverse.backend.barniverse_backend.repository.OfferRepository;
import at.barniverse.backend.barniverse_backend.transformer.OfferTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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
        return getEntity(offerRepository, offerTransformer, id);
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
}
