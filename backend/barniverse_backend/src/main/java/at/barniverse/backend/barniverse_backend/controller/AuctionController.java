package at.barniverse.backend.barniverse_backend.controller;

import at.barniverse.backend.barniverse_backend.dto.AuctionDto;
import at.barniverse.backend.barniverse_backend.services.AuctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * controller with basic CRUD routing for auction related URLs
 */
@RestController()
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
    public ResponseEntity<Object> addAuction(@RequestBody AuctionDto auctionDto) {
        return auctionService.addAuction(auctionDto);
    }

    /**
     * get all saved auctions from the database
     * @return response with corresponding status code and loaded auction dtos or error message in case of failure
     */
    @GetMapping(path="/auctions")
    public ResponseEntity<Object> getAuctions() {
        return auctionService.getAuctions();
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

}
