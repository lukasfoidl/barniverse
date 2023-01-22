package at.barniverse.backend.barniverse_backend.services;

import at.barniverse.backend.barniverse_backend.exception.BarniverseException;
import at.barniverse.backend.barniverse_backend.dto.AuctionDto;
import at.barniverse.backend.barniverse_backend.enums.AuctionState;
import at.barniverse.backend.barniverse_backend.model.Auction;
import at.barniverse.backend.barniverse_backend.repository.AuctionRepository;
import at.barniverse.backend.barniverse_backend.transformer.AuctionTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static at.barniverse.backend.barniverse_backend.configuration.Context.DATABASE_ERROR;

/**
 * service with auction related functionality
 */
@Service
public class AuctionService extends BaseService {

    @Autowired private AuctionRepository auctionRepository;
    @Autowired private AuctionTransformer auctionTransformer;
    @Autowired private AuctionValidationService auctionValidationService;

    /**
     * add an auction to the database
     * @param auctionDto dto which should be saved
     * @return response with corresponding status code and error message in case of failure
     */
    public void addAuction(AuctionDto auctionDto) throws BarniverseException {
        auctionDto.setState(AuctionState.active);
        addEntity(auctionRepository, auctionTransformer, auctionValidationService, auctionDto);
    }

    /**
     * get all saved auctions from the database which are not closed yet (running or before running)
     * @return response with corresponding status code and loaded auction dtos or error message in case of failure
     */
    public List<AuctionDto> getNotClosedAuctions() throws BarniverseException {
        List<Auction> auctions;
        try {
            auctions = auctionRepository.findAllByEndDateAfter(LocalDateTime.now());
        } catch (Exception exception) {
            throw new BarniverseException(List.of(DATABASE_ERROR), HttpStatus.INTERNAL_SERVER_ERROR, exception);
        }

        return convertToDto(auctionTransformer, auctions);
    }

    /**
     * get all saved auctions from the database which are active and are not closed yet (running or before running)
     * @return response with corresponding status code and loaded auction dtos or error message in case of failure
     */
    public List<AuctionDto> getNotClosedActiveAuctions() throws BarniverseException {
        List<Auction> auctions;
        try {
            auctions = auctionRepository.findAllByStateAndEndDateAfter(AuctionState.active, LocalDateTime.now());
        } catch (Exception exception) {
            throw new BarniverseException(List.of(DATABASE_ERROR), HttpStatus.INTERNAL_SERVER_ERROR, exception);
        }

        return convertToDto(auctionTransformer, auctions);
    }

    /**
     * get all saved auctions from the database of a specific user
     * @param id id of the specific user
     * @return response with corresponding status code and loaded auction dtos or error message in case of failure
     */
    public List<AuctionDto> getMyAuctions(int id) throws BarniverseException {
        List<Auction> auctions;
        try {
            auctions = auctionRepository.findAllByUserId(id);
        } catch (Exception exception) {
            throw new BarniverseException(List.of(DATABASE_ERROR), HttpStatus.INTERNAL_SERVER_ERROR, exception);
        }

        return convertToDto(auctionTransformer, auctions);
    }

    /**
     * update specific auction in the database
     * @param auctionDto dto which should be updated (with id)
     */
    public void updateAuction(AuctionDto auctionDto) throws BarniverseException {
        updateEntity(auctionRepository, auctionTransformer, auctionValidationService, auctionDto, auctionDto.getId());
    }

    /**
     * toggles the auction state of a specific auction (locked or active)
     * @param id id of the specific auction
     * @return response with corresponding status code and error message in case of failure
     */
    public AuctionState toggleState(int id) throws BarniverseException {
        Auction auction = getEntity(auctionRepository, id);

        // validation not with standard validation because only updating state (otherwise possible startDate conflict with @Future annotation)
        // and task specific validation
        auctionValidationService.validateTaskToggleAuction(auction);

        AuctionState state = AuctionState.active;
        if (auction.getState() == AuctionState.active) {
            state = AuctionState.locked;
        }

        // saving not with BaseService because only updating state (otherwise possible startDate conflict with @Future annotation)
        try {
            auctionRepository.updateState(id, state);
            return state;
        } catch (Exception exception) {
            throw new BarniverseException(List.of(DATABASE_ERROR), HttpStatus.INTERNAL_SERVER_ERROR, exception);
        }
    }

}
