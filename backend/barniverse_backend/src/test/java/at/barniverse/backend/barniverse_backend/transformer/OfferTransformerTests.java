package at.barniverse.backend.barniverse_backend.transformer;

import at.barniverse.backend.barniverse_backend.dto.AuctionDto;
import at.barniverse.backend.barniverse_backend.dto.OfferDto;
import at.barniverse.backend.barniverse_backend.dto.UserDto;
import at.barniverse.backend.barniverse_backend.enums.OfferState;
import at.barniverse.backend.barniverse_backend.model.Auction;
import at.barniverse.backend.barniverse_backend.model.Offer;
import at.barniverse.backend.barniverse_backend.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class OfferTransformerTests {

    @InjectMocks private OfferTransformer offerTransformer;
    @Mock private UserTransformer userTransformer;
    @Mock private AuctionTransformer auctionTransformer;

    private User user;
    private UserDto userDto;
    private Auction auction;
    private AuctionDto auctionDto;

    @BeforeEach
    void setup(){
        user = new User();
        user.setId(1);
        userDto = new UserDto();
        userDto.setId(1);

        auction = new Auction();
        auction.setId(1);
        auctionDto = new AuctionDto();
        auctionDto.setId(1);
    }

    @Test
    public void convertToDtoTest() {
        // given
        int offerId = 1;
        double price = 100.0;
        int quantity = 10;
        LocalDateTime deliveryDate = LocalDateTime.of(2022, 12, 1, 10, 0, 0);
        OfferState state = OfferState.running;

        Offer offer = new Offer();
        offer.setId(offerId);
        offer.setPrice(price);
        offer.setQuantity(quantity);
        offer.setDeliveryDate(deliveryDate);
        offer.setState(state);
        offer.setUser(user);
        offer.setAuction(auction);

        OfferDto expectedAuctionDto = new OfferDto();
        expectedAuctionDto.setId(offerId);
        expectedAuctionDto.setPrice(price);
        expectedAuctionDto.setQuantity(quantity);
        expectedAuctionDto.setDeliveryDate(deliveryDate);
        expectedAuctionDto.setState(state);
        expectedAuctionDto.setUser(userDto);
        expectedAuctionDto.setAuction(auctionDto);

        // when
        Mockito.when(userTransformer.convertToDto(any(User.class))).thenReturn(userDto);
        Mockito.when(auctionTransformer.convertToDto(any(Auction.class))).thenReturn(auctionDto);

        OfferDto actualOfferDto = offerTransformer.convertToDto(offer);

        // assert
        Assertions.assertEquals(expectedAuctionDto.getId(), actualOfferDto.getId());
        Assertions.assertEquals(expectedAuctionDto.getPrice(), actualOfferDto.getPrice());
        Assertions.assertEquals(expectedAuctionDto.getQuantity(), actualOfferDto.getQuantity());
        Assertions.assertEquals(expectedAuctionDto.getDeliveryDate(), actualOfferDto.getDeliveryDate());
        Assertions.assertEquals(expectedAuctionDto.getState(), actualOfferDto.getState());
        Assertions.assertEquals(expectedAuctionDto.getUser().getId(), actualOfferDto.getUser().getId());
        Assertions.assertEquals(expectedAuctionDto.getAuction().getId(), actualOfferDto.getAuction().getId());
    }

    @Test
    public void convertToEntityTest() {
        // given
        int offerId = 1;
        double price = 100.0;
        int quantity = 10;
        LocalDateTime deliveryDate = LocalDateTime.of(2022, 12, 1, 10, 0, 0);
        OfferState state = OfferState.running;

        OfferDto offerDto = new OfferDto();
        offerDto.setId(offerId);
        offerDto.setPrice(price);
        offerDto.setQuantity(quantity);
        offerDto.setDeliveryDate(deliveryDate);
        offerDto.setState(state);
        offerDto.setUser(userDto);
        offerDto.setAuction(auctionDto);

        Offer expectedOffer = new Offer();
        expectedOffer.setId(0); // id gets set from database
        expectedOffer.setPrice(price);
        expectedOffer.setQuantity(quantity);
        expectedOffer.setDeliveryDate(deliveryDate);
        expectedOffer.setState(state);
        expectedOffer.setUser(user);
        expectedOffer.setAuction(auction);

        // when
        Offer actualOffer = offerTransformer.convertToEntity(offerDto);

        // assert
        Assertions.assertEquals(expectedOffer.getId(), actualOffer.getId());
        Assertions.assertEquals(expectedOffer.getPrice(), actualOffer.getPrice());
        Assertions.assertEquals(expectedOffer.getQuantity(), actualOffer.getQuantity());
        Assertions.assertEquals(expectedOffer.getDeliveryDate(), actualOffer.getDeliveryDate());
        Assertions.assertEquals(expectedOffer.getState(), actualOffer.getState());
        Assertions.assertEquals(expectedOffer.getUser().getId(), actualOffer.getUser().getId());
        Assertions.assertEquals(expectedOffer.getAuction().getId(), actualOffer.getAuction().getId());
    }

    @Test
    public void repairEntityTest() {
        // given
        int offerId = 1;
        double price = 100.0;
        int quantity = 10;
        LocalDateTime deliveryDate = LocalDateTime.of(2022, 12, 1, 10, 0, 0);
        OfferState state = OfferState.running;

        User fakeUser = new User();
        fakeUser.setId(99);
        Auction fakeAuction = new Auction();
        fakeAuction.setId(99);

        Offer offer = new Offer();
        offer.setId(99);
        offer.setPrice(price);
        offer.setQuantity(quantity);
        offer.setDeliveryDate(deliveryDate);
        offer.setState(OfferState.rejected);
        offer.setUser(fakeUser);
        offer.setAuction(fakeAuction);

        Offer dbOffer = new Offer();
        dbOffer.setId(offerId);
        dbOffer.setPrice(price);
        dbOffer.setQuantity(quantity);
        dbOffer.setDeliveryDate(deliveryDate);
        dbOffer.setState(state);
        dbOffer.setUser(user);
        dbOffer.setAuction(auction);

        // when
        Offer actualOffer = offerTransformer.repairEntity(offer, dbOffer);

        // assert
        Assertions.assertEquals(dbOffer.getId(), actualOffer.getId());
        Assertions.assertEquals(dbOffer.getPrice(), actualOffer.getPrice());
        Assertions.assertEquals(dbOffer.getQuantity(), actualOffer.getQuantity());
        Assertions.assertEquals(dbOffer.getDeliveryDate(), actualOffer.getDeliveryDate());
        Assertions.assertEquals(dbOffer.getState(), actualOffer.getState());
        Assertions.assertEquals(dbOffer.getUser().getId(), actualOffer.getUser().getId());
        Assertions.assertEquals(dbOffer.getAuction().getId(), actualOffer.getAuction().getId());
    }
}
