package at.barniverse.backend.barniverse_backend.controller;

import at.barniverse.backend.barniverse_backend.dto.OfferDto;
import at.barniverse.backend.barniverse_backend.services.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static at.barniverse.backend.barniverse_backend.configuration.Context.CORS_ORIGINS;

/**
 * controller with basic CRUD routing for offer related URLs
 */
@RestController
@CrossOrigin(origins = CORS_ORIGINS)
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
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<Object> addOffer(@RequestBody OfferDto offerDto) {
        return offerService.addOffer(offerDto);
    }

    /**
     * get all saved offers from the database
     * @return response with corresponding status code and loaded offer dtos or error message in case of failure
     */
    @GetMapping(path="/offers")
    public ResponseEntity<Object> getOffers() {
        return offerService.getOffers();
    }

    /**
     * get all saved offers from a specific auction
     * @param id id of the specific auction
     * @return response with corresponding status code and loaded offer dtos or error message in case of failure
     */
    @GetMapping(path="/auctions/{id}/offers")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<Object> getOffersFromAuction(@PathVariable int id) {
        return offerService.getOffersFromAuction(id);
    }

    /**
     * get all saved offers from a specific user
     * @param id id of the specific user
     * @return response with corresponding status code and loaded offer dtos or error message in case of failure
     */
    @GetMapping(path="/users/{id}/offers")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<Object> getOffersFromUser(@PathVariable int id) {
        return offerService.getOffersFromUser(id);
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

    /**
     * update specific offer state to accept, <br>
     * update offer state to rejected for all other offers of the accepted offers auction
     * @param id id of the specific offer
     * @return response with corresponding status code and error message in case of failure
     */
    @PutMapping(path="/offers/{id}/accept")
    public ResponseEntity<Object> acceptOffer(@PathVariable int id) {
        return offerService.acceptOffer(id);
    }
}
