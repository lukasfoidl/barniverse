package at.barniverse.backend.barniverse_backend.transformer;

import at.barniverse.backend.barniverse_backend.dto.OfferDto;
import at.barniverse.backend.barniverse_backend.model.Auction;
import at.barniverse.backend.barniverse_backend.model.Offer;
import at.barniverse.backend.barniverse_backend.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * transforms offer entities and dtos
 */
@Component
public class OfferTransformer implements ITransformer<Offer, OfferDto> {

    @Autowired
    private UserTransformer userTransformer;

    @Autowired
    private AuctionTransformer auctionTransformer;

    /**
     * transform offer entity to offer dto, <br>
     * user property of entity will be transformed to a user dto by UserTransformer, <br>
     * auction property of entity will be transformed to an auction dto by AuctionTransformer
     * @param offer entity which should be transformed
     * @return offer dto
     */
    @Override
    public OfferDto convertToDto(Offer offer) {
        OfferDto offerDto = new OfferDto();

        offerDto.setId(offer.getId());
        offerDto.setPrice(offer.getPrice());
        offerDto.setQuantity(offer.getQuantity());
        offerDto.setDeliveryDate(offer.getDeliveryDate());
        offerDto.setState(offer.getState());
        offerDto.setUser(userTransformer.convertToDto(offer.getUser()));
        offerDto.setAuction(auctionTransformer.convertToDto(offer.getAuction()));

        return offerDto;
    }

    /**
     * transform offer dto to offer entity, <br>
     * id property does NOT get transformed from dto to entity, because id gets set from the database automatically, <br>
     * user property of dto will NOT be transformed to a user entity,
     * just the id gets set because cascading is not enabled and only id is necessary for the foreign key, <br>
     * auction property of dto will NOT be transformed to an auction entity,
     * just the id gets set because cascading is not enabled and only id is necessary for the foreign key
     * @param offerDto dto which should be transformed
     * @return offer entity
     */
    @Override
    public Offer convertToEntity(OfferDto offerDto) {
        Offer offer = new Offer();

        // id gets set from database
        offer.setPrice(offerDto.getPrice());
        offer.setQuantity(offerDto.getQuantity());
        offer.setDeliveryDate(offerDto.getDeliveryDate());
        offer.setState(offerDto.getState());

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

    /**
     * repair offer entity after transformation in case of update (PUT), <br>
     * id gets set to update entity, <br>
     * user property gets set to user property from database, because the user cannot be changed after the creation of an offer entity, <br>
     * auction property gets set to auction property from database, because the auction cannot be changed after the creation of an offer entity, <br>
     * state property gets set to state property from database, because the state of an offer can not be updated with standard offer update (PUT)
     * @param offer entity which needs to be repaired
     * @param dbOffer entity with the missing data
     * @return repaired entity
     */
    @Override
    public Offer repairEntity(Offer offer, Offer dbOffer) {
        offer.setId(dbOffer.getId()); // set id to update existing entity
        offer.setUser(dbOffer.getUser()); // user can not be changed after creation of an offer
        offer.setAuction(dbOffer.getAuction()); // auction can not be changed after creation of an offer
        offer.setState(dbOffer.getState()); // state can not be updated with standard offer update (PUT)
        return offer;
    }
}
