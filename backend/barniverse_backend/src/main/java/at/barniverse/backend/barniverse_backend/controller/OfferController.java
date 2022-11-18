package at.barniverse.backend.barniverse_backend.controller;

import at.barniverse.backend.barniverse_backend.dto.OfferDto;
import at.barniverse.backend.barniverse_backend.services.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * controller with basic CRUD routing for offer related URLs
 */
@RestController
@RequestMapping(path = "/api")
public class OfferController {

    @Autowired
    private OfferService offerService;

    /**
     * add an offer to the database
     * @param offerDto object sent from the client
     * @return response with corresponding status code and error message in case of failure
     */
    @PostMapping(path="/offers")
    public ResponseEntity<Object> addOffer(@RequestBody OfferDto offerDto) {
        return offerService.addOffer(offerDto);
    }

    /**
     *get all saved offers from the database
     * @return response with corresponding status code and loaded offer dtos or error message in case of failure
     */
    @GetMapping(path="/offers")
    public ResponseEntity<Object> getOffers() {
        return offerService.getOffers();
    }

    /**
     * get specific offer from the database
     * @param id id of the specific offer
     * @return response with corresponding status code and loaded offer dto or error message in case of failure
     */
    @GetMapping(path="/offers/{id}")
    public ResponseEntity<Object> getOffer(@PathVariable int id) {
        return offerService.getOffer(id);
    }

    //TODO: Safety alert! Offers can be updated only with Id.
    /**
     * update specific offer in the database
     * @param offerDto object sent from the client (with id)
     * @return response with corresponding status code and error message in case of failure
     */
    @PutMapping(path="/offers")
    public ResponseEntity<Object> updateOffer(@RequestBody OfferDto offerDto) {
        return offerService.updateOffer(offerDto);
    }

    //TODO: Safety alert! Offers can be deleted only with Id.
    /**
     * delete specific offer from the database
     * @param id id of the specific offer
     * @return response with corresponding status code and error message in case of failure
     */
    @DeleteMapping(path="/offers/{id}")
    public ResponseEntity<Object> deleteOffer(@PathVariable int id) {
        return offerService.deleteOffer(id);
    }

}
