package at.barniverse.backend.barniverse_backend.controller;

import at.barniverse.backend.barniverse_backend.dto.AuctionDto;
import at.barniverse.backend.barniverse_backend.services.AuctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// auction controller with basic CRUD routing
@RestController()
@RequestMapping(path = "/api")
public class AuctionController {

    @Autowired
    private AuctionService auctionService;

    // create new auction
    @PostMapping(path="/auctions")
    public ResponseEntity<Object> addAuction(@RequestBody AuctionDto auctionDto) {
        return auctionService.addAuction(auctionDto);
    }

    // get all auctions
    @GetMapping(path="/auctions")
    public ResponseEntity<Object> getAuctions() {
        return auctionService.getAuctions();
    }

    // get specific auction
    @GetMapping(path="/auctions/{id}")
    public ResponseEntity<Object> getAuction(@PathVariable int id) {
        return auctionService.getAuction(id);
    }

    // update specific auction
    //TODO: Safety alert! Auctions can be updated only with Id.
    @PutMapping(path="/auctions")
    public ResponseEntity<Object> updateAuction(@RequestBody AuctionDto auctionDto) {
        return auctionService.updateAuction(auctionDto);
    }

    // delete specific auction
    //TODO: Safety alert! Auctions can be deleted only with Id.
    @DeleteMapping(path="/auctions/{id}")
    public ResponseEntity<Object> deleteAuction(@PathVariable int id) {
        return auctionService.deleteAuction(id);
    }

}
