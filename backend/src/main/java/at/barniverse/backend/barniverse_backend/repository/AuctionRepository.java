package at.barniverse.backend.barniverse_backend.repository;

import at.barniverse.backend.barniverse_backend.model.Auction;
import org.springframework.data.repository.CrudRepository;

// This will be AUTO IMPLEMENTED by Spring into a Bean called auctionRepository
// CRUD refers Create, Read, Update, Delete

public interface AuctionRepository extends CrudRepository<Auction, Integer> {

}
