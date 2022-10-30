package at.barniverse.backend.barniverse_backend.transformer;

import at.barniverse.backend.barniverse_backend.dto.OfferDto;
import at.barniverse.backend.barniverse_backend.model.Auction;
import at.barniverse.backend.barniverse_backend.model.Offer;
import at.barniverse.backend.barniverse_backend.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

// offer transformer for convertions between entity <-> dto
@Component
public class OfferTransformer implements ITransformer<Offer, OfferDto> {

    @Autowired
    private UserTransformer userTransformer;

    @Autowired
    private AuctionTransformer auctionTransformer;

    @Override
    public OfferDto convertToDto(Offer offer) {
        OfferDto offerDto = new OfferDto();

        offerDto.setId(offer.getId());
        offerDto.setPrice(offer.getPrice());
        offerDto.setQuantity(offer.getQuantity());
        offerDto.setDeliveryDate(offer.getDeliveryDate());
        offerDto.setStatus(offer.getStatus());
        offerDto.setUser(userTransformer.convertToDto(offer.getUser()));
        offerDto.setAuction(auctionTransformer.convertToDto(offer.getAuction()));

        return offerDto;
    }

    @Override
    public Offer convertToEntity(OfferDto offerDto) {
        Offer offer = new Offer();

        // id gets set from database
        offer.setPrice(offerDto.getPrice());
        offer.setQuantity(offerDto.getQuantity());
        offer.setDeliveryDate(offerDto.getDeliveryDate());
        offer.setStatus(offerDto.getStatus());

        // no cascading enabled, only id for foreign key relevant, sub entities irrelevant
        User user = new User();
        user.setId(offerDto.getUser().getId());
        offer.setUser(user);

        // no cascading enabled, only id for foreign key relevant, sub entities irrelevant
        Auction auction = new Auction();
        auction.setId(offerDto.getAuction().getId());
        offer.setAuction(auction);

        return offer;
    }

    // repair entity in case of update (PUT)
    @Override
    public Offer repairEntity(Offer offer, Offer dbOffer) {
        offer.setId(dbOffer.getId()); // set id to update existing entity
        offer.setUser(dbOffer.getUser()); // user can not be changed after creation of an offer
        offer.setAuction(dbOffer.getAuction()); // auction can not be changed after creation of an offer
        return offer;
    }
}
