package at.barniverse.backend.barniverse_backend.services;

import at.barniverse.backend.barniverse_backend.dto.OfferDto;
import at.barniverse.backend.barniverse_backend.repository.OfferRepository;
import at.barniverse.backend.barniverse_backend.transformer.OfferTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

// service with offer related functionality
@Service
public class OfferService extends BaseService {

    @Autowired
    private OfferRepository offerRepository;

    @Autowired
    private OfferTransformer offerTransformer;

    // create offer
    public ResponseEntity<Object> addOffer(OfferDto offerDto) {
        return addEntity(offerRepository, offerTransformer, offerDto);
    }

    // read offer
    public ResponseEntity<Object> getOffers() {
        return getEntities(offerRepository, offerTransformer);
    }

    // read specific offer
    public ResponseEntity<Object> getOffer(int id) {
        return getEntity(offerRepository, offerTransformer, id);
    }

    // update offer
    public ResponseEntity<Object> updateOffer(OfferDto offerDto) {
        return updateEntity(offerRepository, offerTransformer, offerDto);
    }

    // delete offer
    public ResponseEntity<Object> deleteOffer(int id) {
        return deleteEntity(offerRepository, id);
    }
}
