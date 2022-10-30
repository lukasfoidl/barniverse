package at.barniverse.backend.barniverse_backend.transformer;

import at.barniverse.backend.barniverse_backend.dto.AuctionDto;
import at.barniverse.backend.barniverse_backend.model.Auction;
import at.barniverse.backend.barniverse_backend.model.Product;
import at.barniverse.backend.barniverse_backend.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

// auction transformer for convertions between entity <-> dto
@Component
public class AuctionTransformer implements ITransformer<Auction, AuctionDto> {

    @Autowired
    private UserTransformer userTransformer;

    @Autowired
    private ProductTransformer productTransformer;

    @Override
    public AuctionDto convertToDto(Auction auction) {
        AuctionDto auctionDto = new AuctionDto();

        auctionDto.setId(auction.getId());
        auctionDto.setTitle(auction.getTitle());
        auctionDto.setDescription(auction.getDescription());
        auctionDto.setMinPrice(auction.getMinPrice());
        auctionDto.setMaxPrice(auction.getMaxPrice());
        auctionDto.setMinQuantity(auction.getMinQuantity());
        auctionDto.setMaxQuantity(auction.getMaxQuantity());
        auctionDto.setStartDeliveryDate(auction.getStartDeliveryDate());
        auctionDto.setEndDeliveryDate(auction.getEndDeliveryDate());
        auctionDto.setStartDate(auction.getStartDate());
        auctionDto.setEndDate(auction.getEndDate());
        auctionDto.setLocked(auction.isLocked());
        auctionDto.setUser(userTransformer.convertToDto(auction.getUser()));
        auctionDto.setProduct(productTransformer.convertToDto(auction.getProduct()));

        return auctionDto;
    }

    @Override
    public Auction convertToEntity(AuctionDto auctionDto) {
        Auction auction = new Auction();

        // id gets set from database
        auction.setTitle(auctionDto.getTitle());
        auction.setDescription(auctionDto.getDescription());
        auction.setMinPrice(auctionDto.getMinPrice());
        auction.setMaxPrice(auctionDto.getMaxPrice());
        auction.setMinQuantity(auctionDto.getMinQuantity());
        auction.setMaxQuantity(auctionDto.getMaxQuantity());
        auction.setStartDeliveryDate(auctionDto.getStartDeliveryDate());
        auction.setEndDeliveryDate(auctionDto.getEndDeliveryDate());
        auction.setStartDate(auctionDto.getStartDate());
        auction.setEndDate(auctionDto.getEndDate());
        auction.setLocked(auctionDto.isLocked());

        // no cascading enabled, only id for foreign key relevant, sub entities irrelevant
        User user = new User();
        user.setId(auctionDto.getUser().getId());
        auction.setUser(user);

        // no cascading enabled, only id for foreign key relevant, sub entities irrelevant
        Product product = new Product();
        product.setId(auctionDto.getProduct().getId());
        auction.setProduct(product);

        return auction;
    }

    // repair entity in case of update (PUT)
    @Override
    public Auction repairEntity(Auction auction, Auction dbAuction) {
        auction.setId(dbAuction.getId()); // set id to update existing entity
        auction.setUser(dbAuction.getUser()); // user can not be changed after creation of an auction
        auction.setProduct(dbAuction.getProduct()); // product can not be changed after creation of an auction
        return auction;
    }
}
