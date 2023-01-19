package at.barniverse.backend.barniverse_backend.repository;

import at.barniverse.backend.barniverse_backend.enums.AuctionState;
import at.barniverse.backend.barniverse_backend.model.Auction;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

/**
 * basic repository for auction entity
 * will be auto implemented by Spring into a Bean called auctionRepository
 */
@Transactional
public interface AuctionRepository extends CrudRepository<Auction, Integer> {

    @Modifying
    @Query("update Auction a set a.state = :state where a.id = :id")
    void updateState(@Param(value = "id") int id, @Param(value = "state") AuctionState state);

}
