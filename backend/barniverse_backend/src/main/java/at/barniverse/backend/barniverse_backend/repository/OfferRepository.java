package at.barniverse.backend.barniverse_backend.repository;

import at.barniverse.backend.barniverse_backend.enums.OfferState;
import at.barniverse.backend.barniverse_backend.model.Auction;
import at.barniverse.backend.barniverse_backend.model.Offer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

/**
 * basic repository for offer entity
 */
public interface OfferRepository extends CrudRepository<Offer, Integer> {

    /**
     * get all offers from a specific auction
     * @param auction_id id of the auction which should be filtered
     * @return offers of a specific auction, empty list if no offers available
     */
    List<Offer> findAllByAuction_Id(int auction_id);

    /**
     * get all offers from a specific user
     * @param user_id id of the user which should be filtered
     * @return offers of a specific user, empty list if no offers available
     */
    List<Offer> findAllByUser_Id(int user_id);

    /**
     * check if offer with a specific id and a specific state exists
     * @param id id of the specific user
     * @param state state which should be filtered
     * @return true if auction exists, otherwise false
     */
    boolean existsByIdAndState(int id, OfferState state);

    /**
     * get the number of offers with a specific state
     * @param state state which should be filtered
     * @return number of found offers, 0 if no offers found
     */
    long countAllByState(OfferState state);
}
