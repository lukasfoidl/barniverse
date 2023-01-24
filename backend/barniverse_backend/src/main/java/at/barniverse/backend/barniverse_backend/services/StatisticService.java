package at.barniverse.backend.barniverse_backend.services;

import at.barniverse.backend.barniverse_backend.dto.StatisticDto;
import at.barniverse.backend.barniverse_backend.enums.AuctionState;
import at.barniverse.backend.barniverse_backend.enums.OfferState;
import at.barniverse.backend.barniverse_backend.enums.ProductState;
import at.barniverse.backend.barniverse_backend.exception.BarniverseException;
import at.barniverse.backend.barniverse_backend.repository.AuctionRepository;
import at.barniverse.backend.barniverse_backend.repository.OfferRepository;
import at.barniverse.backend.barniverse_backend.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

import static at.barniverse.backend.barniverse_backend.configuration.Context.DATABASE_ERROR;

@Service
public class StatisticService {

    @Autowired private ProductRepository productRepository;
    @Autowired private AuctionRepository auctionRepository;
    @Autowired private OfferRepository offerRepository;

    public List<StatisticDto> getStatistics() throws BarniverseException {
        List<StatisticDto> statistics = new ArrayList<>(Collections.emptyList());
        try {
            statistics.add(new StatisticDto(productRepository.countAllByState(ProductState.active), "products available"));
            statistics.add(new StatisticDto(
                    auctionRepository.countAllByStartDateBeforeAndEndDateAfterAndState(LocalDateTime.now(), LocalDateTime.now(), AuctionState.active),
                    "currently open auctions"));
            statistics.add(new StatisticDto(offerRepository.countAllByState(OfferState.accepted), "closed deals"));
        } catch (Exception exception) {
            throw new BarniverseException(List.of(DATABASE_ERROR), HttpStatus.INTERNAL_SERVER_ERROR, exception);
        }
        return statistics;
    }
}
