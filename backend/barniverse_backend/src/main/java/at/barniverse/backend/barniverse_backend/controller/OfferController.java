package at.barniverse.backend.barniverse_backend.controller;

import at.barniverse.backend.barniverse_backend.exception.BarniverseException;
import at.barniverse.backend.barniverse_backend.dto.OfferDto;
import at.barniverse.backend.barniverse_backend.services.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static at.barniverse.backend.barniverse_backend.configuration.Context.CORS_ORIGINS;

/**
 * controller with basic CRUD routing for offer related URLs
 */
@RestController
@CrossOrigin(origins = CORS_ORIGINS)
@RequestMapping(path = "/api")
public class OfferController {

    @Autowired private OfferService offerService;

    /**
     * add an offer to the database
     * @param offerDto object sent from the client
     * @return response with corresponding status code and error message in case of failure
     */
    @PostMapping(path="/offers")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<Object> addOffer(@RequestBody OfferDto offerDto) throws BarniverseException {
        offerService.addOffer(offerDto);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    /**
     * get all saved offers from a specific auction
     * @param id id of the specific auction
     * @return response with corresponding status code and loaded offer dtos or error message in case of failure
     */
    @GetMapping(path="/auctions/{id}/offers")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<Object> getOffersFromAuction(@PathVariable int id) throws BarniverseException {
        List<OfferDto> results = offerService.getOffersFromAuction(id);
        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    /**
     * get all saved offers from a specific user
     * @param id id of the specific user
     * @return response with corresponding status code and loaded offer dtos or error message in case of failure
     */
    @GetMapping(path="/users/{id}/offers")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<Object> getOffersFromUser(@PathVariable int id) throws BarniverseException {
        List<OfferDto> results = offerService.getOffersFromUser(id);
        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    /**
     * update specific offer state to accept, <br>
     * update offer state to rejected for all other offers of the accepted offers auction
     * @param id id of the specific offer
     * @return response with corresponding status code and error message in case of failure
     */
    @PutMapping(path="/offers/{id}/accept")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<Object> acceptOffer(@PathVariable int id) throws BarniverseException {
        offerService.acceptOffer(id);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
