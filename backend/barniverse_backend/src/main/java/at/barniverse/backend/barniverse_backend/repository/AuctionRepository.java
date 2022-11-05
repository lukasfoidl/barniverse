package at.barniverse.backend.barniverse_backend.repository;

import at.barniverse.backend.barniverse_backend.model.Auction;
import org.springframework.data.repository.CrudRepository;

/**
 * basic repository for auction entity
 * will be auto implemented by Spring into a Bean called auctionRepository
 */
public interface AuctionRepository extends CrudRepository<Auction, Integer> {

}
