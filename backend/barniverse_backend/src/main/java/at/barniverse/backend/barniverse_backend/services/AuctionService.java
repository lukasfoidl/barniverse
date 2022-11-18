package at.barniverse.backend.barniverse_backend.services;

import at.barniverse.backend.barniverse_backend.dto.AuctionDto;
import at.barniverse.backend.barniverse_backend.repository.AuctionRepository;
import at.barniverse.backend.barniverse_backend.transformer.AuctionTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * service with auction related functionality
 */
@Service
public class AuctionService extends BaseService {

    @Autowired
    private AuctionRepository auctionRepository;

    @Autowired
    private AuctionTransformer auctionTransformer;

    @Autowired
    private AuctionValidationService auctionValidationService;

    /**
     * add an auction to the database
     * @param auctionDto dto which should be saved
     * @return response with corresponding status code and error message in case of failure
     */
    public ResponseEntity<Object> addAuction(AuctionDto auctionDto) {
        return addEntity(auctionRepository, auctionTransformer, auctionValidationService, auctionDto);
    }

    /**
     * get all saved auctions from the database
     * @return response with corresponding status code and loaded auction dtos or error message in case of failure
     */
    public ResponseEntity<Object> getAuctions() {
        return getEntities(auctionRepository, auctionTransformer);
    }

    /**
     * get specific auction from the database
     * @param id id of the specific auction
     * @return response with corresponding status code and loaded auction dto or error message in case of failure
     */
    public ResponseEntity<Object> getAuction(int id) {
        return getEntity(auctionRepository, auctionTransformer, id);
    }

    /**
     * update specific auction in the database
     * @param auctionDto dto which should be updated (with id)
     * @return response with corresponding status code and error message in case of failure
     */
    public ResponseEntity<Object> updateAuction(AuctionDto auctionDto) {
        return updateEntity(auctionRepository, auctionTransformer, auctionValidationService, auctionDto);
    }

    /**
     * delete specific auction from the database
     * @param id id of the specific auction
     * @return response with corresponding status code and error message in case of failure
     */
    public ResponseEntity<Object> deleteAuction(int id) {
        return deleteEntity(auctionRepository, id);
    }

}
