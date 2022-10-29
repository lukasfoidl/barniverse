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

        offer.setId(offerDto.getId());
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
}
