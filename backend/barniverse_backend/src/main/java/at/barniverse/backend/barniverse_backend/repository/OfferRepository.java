package at.barniverse.backend.barniverse_backend.repository;

import at.barniverse.backend.barniverse_backend.model.Auction;
import at.barniverse.backend.barniverse_backend.model.Offer;
import org.springframework.data.repository.CrudRepository;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

/**
 * basic repository for offer entity
 * will be auto implemented by Spring into a Bean called offerRepository
 */
public interface OfferRepository extends CrudRepository<Offer, Integer> {

    List<Offer> findByAuction_Id(@NotNull int auction_id);
    List<Offer> findByUser_Id(@NotNull int user_id);

}
