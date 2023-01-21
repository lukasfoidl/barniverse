package at.barniverse.backend.barniverse_backend.services;

import at.barniverse.backend.barniverse_backend.dto.AuctionDto;
import at.barniverse.backend.barniverse_backend.dto.UserDto;
import at.barniverse.backend.barniverse_backend.enums.AuctionState;
import at.barniverse.backend.barniverse_backend.enums.ProductState;
import at.barniverse.backend.barniverse_backend.enums.UserState;
import at.barniverse.backend.barniverse_backend.model.Auction;
import at.barniverse.backend.barniverse_backend.model.User;
import at.barniverse.backend.barniverse_backend.repository.AuctionRepository;
import at.barniverse.backend.barniverse_backend.transformer.AuctionTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static at.barniverse.backend.barniverse_backend.configuration.Context.DATABASE_ERROR;

/**
 * service with auction related functionality
 */
@Service
public class AuctionService extends BaseService {

    @Autowired
    private AuctionRepository auctionRepository;

    @Autowired
    private AuctionTransformer auctionTransformer;

    @Autowired
    private AuctionValidationService auctionValidationService;

    /**
     * add an auction to the database
     * @param auctionDto dto which should be saved
     * @return response with corresponding status code and error message in case of failure
     */
    public ResponseEntity<Object> addAuction(AuctionDto auctionDto) {
        auctionDto.setState(AuctionState.active);
        return addEntity(auctionRepository, auctionTransformer, auctionValidationService, auctionDto);
    }

    /**
     * get all saved auctions from the database which are not closed yet (running or before running)
     * @return response with corresponding status code and loaded auction dtos or error message in case of failure
     */
    public ResponseEntity<Object> getNotClosedAuctions() {
        ResponseEntity<Object> response = getEntities(auctionRepository, auctionTransformer);
        if (response.getStatusCode() != HttpStatus.OK) {
            return response;
        }

        List<AuctionDto> auctionDtoList = (List<AuctionDto>) response.getBody();
        List<AuctionDto> resultList = new ArrayList<>();
        for (AuctionDto auctionDto : auctionDtoList) {
            if (auctionDto.getEndDate().compareTo(LocalDateTime.now()) > 0) {
                resultList.add(auctionDto);
            }
        }
        return new ResponseEntity<>(resultList, HttpStatus.OK);
    }

    /**
     * get all saved auctions from the database which are active and are not closed yet (running or before running)
     * @return response with corresponding status code and loaded auction dtos or error message in case of failure
     */
    public ResponseEntity<Object> getNotClosedActiveAuctions() {
        ResponseEntity<Object> response = getNotClosedAuctions();
        if (response.getStatusCode() != HttpStatus.OK) {
            return response;
        }

        List<AuctionDto> auctionDtoList = (List<AuctionDto>) response.getBody();
        List<AuctionDto> resultList = new ArrayList<>();
        for (AuctionDto auctionDto : auctionDtoList) {
            if (auctionDto.getState() == AuctionState.active) {
                resultList.add(auctionDto);
            }
        }
        return new ResponseEntity<>(resultList, HttpStatus.OK);
    }

    /**
     * get all saved auctions from the database of a specific user
     * @param id id of the specific user
     * @return response with corresponding status code and loaded auction dtos or error message in case of failure
     */
    public ResponseEntity<Object> getMyAuctions(int id) {
        ResponseEntity<Object> response = getEntities(auctionRepository, auctionTransformer);
        if (response.getStatusCode() != HttpStatus.OK) {
            return response;
        }

        List<AuctionDto> auctionDtoList = (List<AuctionDto>) response.getBody();
        List<AuctionDto> resultList = new ArrayList<>();
        for (AuctionDto auctionDto : auctionDtoList) {
            if (auctionDto.getUser().getId() == id) {
                resultList.add(auctionDto);
            }
        }
        return new ResponseEntity<>(resultList, HttpStatus.OK);
    }

    /**
     * get specific auction from the database
     * @param id id of the specific auction
     * @return response with corresponding status code and loaded auction dto or error message in case of failure
     */
    public ResponseEntity<Object> getAuction(int id) {
        return getEntityAsDto(auctionRepository, auctionTransformer, id);
    }

    /**
     * update specific auction in the database
     * @param auctionDto dto which should be updated (with id)
     * @return response with corresponding status code and error message in case of failure
     */
    public ResponseEntity<Object> updateAuction(AuctionDto auctionDto) {
        return updateEntity(auctionRepository, auctionTransformer, auctionValidationService, auctionDto);
    }

    /**
     * delete specific auction from the database
     * @param id id of the specific auction
     * @return response with corresponding status code and error message in case of failure
     */
    public ResponseEntity<Object> deleteAuction(int id) {
        return deleteEntity(auctionRepository, id);
    }

    /**
     * toggles the auction state of a specific auction (locked or active)
     * @param id id of the specific auction
     * @return response with corresponding status code and error message in case of failure
     */
    public ResponseEntity<Object> toggleState(int id) {
        ResponseEntity<Object> response = getEntity(auctionRepository, id);
        if (response.getStatusCode() != HttpStatus.OK) {
            return response;
        }
        Auction auction = (Auction) response.getBody();

        // validation not with standard validation because only updating state (otherwise possible startDate conflict with @Future annotation)
        // and task specific validation
        String error;
        if (!(error = auctionValidationService.validateTaskToggleAuction(auction)).isBlank()) {
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        AuctionState state = AuctionState.active;
        if (auction.getState() == AuctionState.active) {
            state = AuctionState.locked;
        } else if (auction.getState() == AuctionState.locked) {
            state = AuctionState.active;
        }

        // saving not with BaseService because only updating state (otherwise possible startDate conflict with @Future annotation)
        try {
            auctionRepository.updateState(auction.getId(), state);
            return new ResponseEntity<>(state, HttpStatus.OK);
        } catch (Exception exception) {
            return new ResponseEntity<>(DATABASE_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
