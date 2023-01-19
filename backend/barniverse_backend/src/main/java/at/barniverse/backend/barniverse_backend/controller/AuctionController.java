package at.barniverse.backend.barniverse_backend.controller;

import at.barniverse.backend.barniverse_backend.dto.AuctionDto;
import at.barniverse.backend.barniverse_backend.services.AuctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static at.barniverse.backend.barniverse_backend.configuration.Context.CORS_ORIGINS;

/**
 * controller with basic CRUD routing for auction related URLs
 */
@RestController()
@CrossOrigin(origins = CORS_ORIGINS)
@RequestMapping(path = "/api")
public class AuctionController {

    @Autowired
    private AuctionService auctionService;

    /**
     * add an auction to the database
     * @param auctionDto object sent from the client
     * @return response with corresponding status code and error message in case of failure
     */
    @PostMapping(path="/auctions")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<Object> addAuction(@RequestBody AuctionDto auctionDto) {
        return auctionService.addAuction(auctionDto);
    }

    /**
     * get all saved auctions from the database which are not closed yet (running or before running)
     * @return response with corresponding status code and loaded auction dtos or error message in case of failure
     */
    @GetMapping(path="/auctions/notClosed")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<Object> getNotClosedAuctions() {
        return auctionService.getNotClosedAuctions();
    }

    /**
     * get all saved auctions from the database which are active and are not closed yet (running or before running)
     * @return response with corresponding status code and loaded auction dtos or error message in case of failure
     */
    @GetMapping(path="/auctions")
    public ResponseEntity<Object> getNotClosedActiveAuctions() {
        return auctionService.getNotClosedActiveAuctions();
    }

    /**
     * get all saved auctions from the database of a specific user
     * @param id id of the specific user
     * @return response with corresponding status code and loaded auction dtos or error message in case of failure
     */
    @GetMapping(path="/myAuctions/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<Object> getMyAuctions(@PathVariable int id) {
        return auctionService.getMyAuctions(id);
    }

    /**
     * get specific auction from the database
     * @param id id of the specific auction
     * @return response with corresponding status code and loaded auction dto or error message in case of failure
     */
    @GetMapping(path="/auctions/{id}")
    public ResponseEntity<Object> getAuction(@PathVariable int id) {
        return auctionService.getAuction(id);
    }


    //TODO: Safety alert! Auctions can be updated only with Id.
    /**
     * update specific auction in the database
     * @param auctionDto object sent from the client (with id)
     * @return response with corresponding status code and error message in case of failure
     */
    @PutMapping(path="/auctions")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<Object> updateAuction(@RequestBody AuctionDto auctionDto) {
        return auctionService.updateAuction(auctionDto);
    }

    //TODO: Safety alert! Auctions can be deleted only with Id.
    /**
     * delete specific auction from the database
     * @param id id of the specific auction
     * @return response with corresponding status code and error message in case of failure
     */
    @DeleteMapping(path="/auctions/{id}")
    public ResponseEntity<Object> deleteAuction(@PathVariable int id) {
        return auctionService.deleteAuction(id);
    }

    /**
     * toggles the auction state of a specific auction (locked or active)
     * @param id id of the specific auction
     * @return response with corresponding status code and error message in case of failure
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PutMapping(path="/auctions/toggleState/{id}")
    public ResponseEntity<Object> toggleState(@PathVariable int id) {
        return auctionService.toggleState(id);
    }

}
