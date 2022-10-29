package at.barniverse.backend.barniverse_backend.services;

import at.barniverse.backend.barniverse_backend.dto.AuctionDto;
import at.barniverse.backend.barniverse_backend.repository.AuctionRepository;
import at.barniverse.backend.barniverse_backend.transformer.AuctionTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

// service with auction related functionality
@Service
public class AuctionService extends BaseService {

    @Autowired
    private AuctionRepository auctionRepository;

    @Autowired
    private AuctionTransformer auctionTransformer;

    // create auction
    public ResponseEntity<Object> addAuction(AuctionDto auctionDto) {
        return addEntity(auctionRepository, auctionTransformer, auctionDto);
    }

    // read auctions
    public ResponseEntity<Object> getAuctions() {
        return getEntities(auctionRepository, auctionTransformer);
    }

    // read specific auction
    public ResponseEntity<Object> getAuction(int id) {
        return getEntity(auctionRepository, auctionTransformer, id);
    }

    // update auction
    public ResponseEntity<Object> updateAuction(AuctionDto auctionDto) {
        return updateEntity(auctionRepository, auctionTransformer, auctionDto);
    }

    // delete auction
    public ResponseEntity<Object> deleteAuction(int id) {
        return deleteEntity(auctionRepository, id);
    }

}
