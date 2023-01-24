package at.barniverse.backend.barniverse_backend.controller;

import at.barniverse.backend.barniverse_backend.exception.BarniverseException;
import at.barniverse.backend.barniverse_backend.dto.AuctionDto;
import at.barniverse.backend.barniverse_backend.enums.AuctionState;
import at.barniverse.backend.barniverse_backend.services.AuctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static at.barniverse.backend.barniverse_backend.configuration.Context.CORS_ORIGINS;

/**
 * controller with basic CRUD routing for auction related URLs
 */
@RestController()
@CrossOrigin(origins = CORS_ORIGINS)
@RequestMapping(path = "/api")
public class AuctionController {

    @Autowired private AuctionService auctionService;

    /**
     * add an auction to the database
     * @param auctionDto object sent from the client
     * @return response with corresponding status code and error message in case of failure
     */
    @PostMapping(path="/auctions")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<Object> addAuction(@RequestBody AuctionDto auctionDto) throws BarniverseException {
        auctionService.addAuction(auctionDto);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    /**
     * get all saved auctions from the database which are not locked
     * @return response with corresponding status code and loaded auction dtos or error message in case of failure
     */
    @GetMapping(path="/auctions/unlocked")
    public ResponseEntity<Object> getUnlockedAuctions() throws BarniverseException {
        List<AuctionDto> results = auctionService.getUnlockedAuctions();
        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    /**
     * get all saved auctions from the database
     * @return response with corresponding status code and loaded auction dtos or error message in case of failure
     */
    @GetMapping(path="/auctions")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<Object> getAuctions() throws BarniverseException {
        List<AuctionDto> results = auctionService.getAuctions();
        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    /**
     * get all saved auctions from the database of a specific user
     * @param id id of the specific user
     * @return response with corresponding status code and loaded auction dtos or error message in case of failure
     */
    @GetMapping(path="/myAuctions/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<Object> getMyAuctions(@PathVariable int id) throws BarniverseException {
        List<AuctionDto> results = auctionService.getMyAuctions(id);
        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    //TODO: Safety alert! Auctions can be updated only with Id.
    /**
     * update specific auction in the database
     * @param auctionDto object sent from the client (with id)
     * @return response with corresponding status code and error message in case of failure
     */
    @PutMapping(path="/auctions")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<Object> updateAuction(@RequestBody AuctionDto auctionDto) throws BarniverseException {
        auctionService.updateAuction(auctionDto);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    /**
     * toggles the auction state of a specific auction (locked or active)
     * @param id id of the specific auction
     * @return response with corresponding status code and error message in case of failure
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PutMapping(path="/auctions/{id}/toggleState")
    public ResponseEntity<Object> toggleState(@PathVariable int id) throws BarniverseException {
        AuctionState state =  auctionService.toggleState(id);
        return new ResponseEntity<>(state, HttpStatus.OK);
    }

}
