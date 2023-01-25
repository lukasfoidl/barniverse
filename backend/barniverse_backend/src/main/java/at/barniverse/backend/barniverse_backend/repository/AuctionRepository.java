package at.barniverse.backend.barniverse_backend.repository;

import at.barniverse.backend.barniverse_backend.enums.AuctionState;
import at.barniverse.backend.barniverse_backend.model.Auction;
import net.bytebuddy.asm.Advice;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

/**
 * basic repository for auction entity
 */
@Transactional
public interface AuctionRepository extends CrudRepository<Auction, Integer> {

    /**
     * update the state of an auction without validating any values
     * @param id id of the auction which should be updated
     * @param state new state
     */
    @Modifying
    @Query("update Auction a set a.state = :state where a.id = :id")
    void updateState(@Param(value = "id") int id, @Param(value = "state") AuctionState state);

    /**
     * get all auctions of specific user
     * @param user_id id of the specific user
     * @return auctions of one specific user
     */
    List<Auction> findAllByUserId(int user_id);

    /**
     * get all auctions in the database
     * @return all auctions in the database
     */
    List<Auction> findAll();

    /**
     * get all auctions with a specific state
     * @param state state which should be filtered
     * @return auctions with the given state
     */
    List<Auction> findAllByState(AuctionState state);

    /**
     * check if auction with specific id and specific state exists
     * @param id id of the specific auction
     * @param state state which should be filtered
     * @return true if auction exists, otherwise false
     */
    boolean existsByIdAndState(int id, AuctionState state);

    /**
     * check if auction with specific id and a start date after a specific date exists
     * @param id id of the specific auction
     * @param startDate start date which should be filtered
     * @return true if auction exists, otherwise false
     */
    boolean existsByIdAndStartDateAfter(int id, LocalDateTime startDate);

    /**
     * checks if auction with specific id, a start date before a specific date and an end date after a specific date exists
     * @param id id of the specific auction
     * @param startDate start date which should be filtered
     * @param endDate end date which should be filtered
     * @return true if auction exists, otherwise false
     */
    boolean existsByIdAndStartDateBeforeAndEndDateAfter(int id, LocalDateTime startDate, LocalDateTime endDate);

    /**
     * get the number of auctions with the start date before a specific date, the end date after a specific date and a specific state
     * @param startDate start date which should be filtered
     * @param endDate end date which should be filtered
     * @param state state which should be filtered
     * @return number of found auctions, 0 if no auctions found
     */
    long countAllByStartDateBeforeAndEndDateAfterAndState(LocalDateTime startDate, LocalDateTime endDate, AuctionState state);
}
