package at.barniverse.backend.barniverse_backend.controller;

import at.barniverse.backend.barniverse_backend.dto.OfferDto;
import at.barniverse.backend.barniverse_backend.services.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// offer controller with basic CRUD routing
@RestController
@RequestMapping(path = "/api")
public class OfferController {

    @Autowired
    private OfferService offerService;

    // create new offer
    @PostMapping(path="/offers")
    public ResponseEntity<Object> addOffer(@RequestBody OfferDto offerDto) {
        return offerService.addOffer(offerDto);
    }

    // get all offers
    @GetMapping(path="/offers")
    public ResponseEntity<Object> getOffers() {
        return offerService.getOffers();
    }

    // get specific offer
    @GetMapping(path="/offers/{id}")
    public ResponseEntity<Object> getOffer(@PathVariable int id) {
        return offerService.getOffer(id);
    }

    // update specific offer
    //TODO: Safety alert! Offers can be updated only with Id.
    @PutMapping(path="/offers")
    public ResponseEntity<Object> updateOffer(@RequestBody OfferDto offerDto) {
        return offerService.updateOffer(offerDto);
    }

    // delete specific offer
    //TODO: Safety alert! Offers can be deleted only with Id.
    @DeleteMapping(path="/offers/{id}")
    public ResponseEntity<Object> deleteOffer(@PathVariable int id) {
        return offerService.deleteOffer(id);
    }

}
