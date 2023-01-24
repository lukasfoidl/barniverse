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
 * will be auto implemented by Spring into a Bean called auctionRepository
 */
@Transactional
public interface AuctionRepository extends CrudRepository<Auction, Integer> {

    @Modifying
    @Query("update Auction a set a.state = :state where a.id = :id")
    void updateState(@Param(value = "id") int id, @Param(value = "state") AuctionState state);

    List<Auction> findAllByStateAndEndDateAfter(AuctionState state, LocalDateTime endDate);
    List<Auction> findAllByEndDateAfter(LocalDateTime endDate);
    List<Auction> findAllByUserId(int user_id);
    boolean existsByIdAndState(int id, AuctionState state);
    boolean existsByIdAndStartDateAfter(int id, LocalDateTime startDate);
    boolean existsByIdAndStartDateBeforeAndEndDateAfter(int id, LocalDateTime startDate, LocalDateTime endDate);

    long countAllByStartDateBeforeAndEndDateAfter(LocalDateTime startDate, LocalDateTime endDate);
}
