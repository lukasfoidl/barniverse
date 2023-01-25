package at.barniverse.backend.barniverse_backend.transformer;

import at.barniverse.backend.barniverse_backend.dto.AuctionDto;
import at.barniverse.backend.barniverse_backend.dto.ProductDto;
import at.barniverse.backend.barniverse_backend.dto.UserDto;
import at.barniverse.backend.barniverse_backend.enums.AuctionState;
import at.barniverse.backend.barniverse_backend.model.Auction;
import at.barniverse.backend.barniverse_backend.model.Product;
import at.barniverse.backend.barniverse_backend.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuctionTransformerTests {

    @InjectMocks private AuctionTransformer auctionTransformer;
    @Mock private UserTransformer userTransformer;
    @Mock private ProductTransformer productTransformer;

    private User user;
    private UserDto userDto;
    private Product product;
    private ProductDto productDto;

    @BeforeEach
    void setup(){
        user = new User();
        user.setId(1);
        userDto = new UserDto();
        userDto.setId(1);

        product = new Product();
        product.setId(1);
        productDto = new ProductDto();
        productDto.setId(1);
    }

    @Test
    public void convertToDtoTest() {
        // given
        int auctionId = 1;
        String title = "Test Auction";
        String description = "This is a test auction";
        double minPrice = 100.0;
        double maxPrice = 200.0;
        int minQuantity = 10;
        int maxQuantity = 20;
        LocalDateTime startDeliveryDate = LocalDateTime.of(2022, 12, 1, 10, 0, 0);
        LocalDateTime endDeliveryDate = LocalDateTime.of(2022, 12, 30, 10, 0, 0);
        LocalDateTime startDate = LocalDateTime.of(2022, 11, 1, 10, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2022, 11, 30, 10, 0, 0);
        AuctionState state = AuctionState.active;

        Auction auction = new Auction();
        auction.setId(auctionId);
        auction.setTitle(title);
        auction.setDescription(description);
        auction.setMinPrice(minPrice);
        auction.setMaxPrice(maxPrice);
        auction.setMinQuantity(minQuantity);
        auction.setMaxQuantity(maxQuantity);
        auction.setStartDeliveryDate(startDeliveryDate);
        auction.setEndDeliveryDate(endDeliveryDate);
        auction.setStartDate(startDate);
        auction.setEndDate(endDate);
        auction.setState(state);
        auction.setUser(user);
        auction.setProduct(product);

        AuctionDto expectedAuctionDto = new AuctionDto();
        expectedAuctionDto.setId(auctionId);
        expectedAuctionDto.setTitle(title);
        expectedAuctionDto.setDescription(description);
        expectedAuctionDto.setMinPrice(minPrice);
        expectedAuctionDto.setMaxPrice(maxPrice);
        expectedAuctionDto.setMinQuantity(minQuantity);
        expectedAuctionDto.setMaxQuantity(maxQuantity);
        expectedAuctionDto.setStartDeliveryDate(startDeliveryDate);
        expectedAuctionDto.setEndDeliveryDate(endDeliveryDate);
        expectedAuctionDto.setStartDate(startDate);
        expectedAuctionDto.setEndDate(endDate);
        expectedAuctionDto.setState(state);
        expectedAuctionDto.setUser(userDto);
        expectedAuctionDto.setProduct(productDto);

        // when
        Mockito.when(userTransformer.convertToDto(any(User.class))).thenReturn(userDto);
        Mockito.when(productTransformer.convertToDto(any(Product.class))).thenReturn(productDto);

        AuctionDto actualAuctionDto = auctionTransformer.convertToDto(auction);

        // assert
        Assertions.assertEquals(expectedAuctionDto.getId(), actualAuctionDto.getId());
        Assertions.assertEquals(expectedAuctionDto.getTitle(), actualAuctionDto.getTitle());
        Assertions.assertEquals(expectedAuctionDto.getDescription(), actualAuctionDto.getDescription());
        Assertions.assertEquals(expectedAuctionDto.getMinPrice(), actualAuctionDto.getMinPrice());
        Assertions.assertEquals(expectedAuctionDto.getMaxPrice(), actualAuctionDto.getMaxPrice());
        Assertions.assertEquals(expectedAuctionDto.getMinQuantity(), actualAuctionDto.getMinQuantity());
        Assertions.assertEquals(expectedAuctionDto.getMaxQuantity(), actualAuctionDto.getMaxQuantity());
        Assertions.assertEquals(expectedAuctionDto.getStartDate(), actualAuctionDto.getStartDate());
        Assertions.assertEquals(expectedAuctionDto.getEndDate(), actualAuctionDto.getEndDate());
        Assertions.assertEquals(expectedAuctionDto.getStartDeliveryDate(), actualAuctionDto.getStartDeliveryDate());
        Assertions.assertEquals(expectedAuctionDto.getEndDeliveryDate(), actualAuctionDto.getEndDeliveryDate());
        Assertions.assertEquals(expectedAuctionDto.getState(), actualAuctionDto.getState());
        Assertions.assertEquals(expectedAuctionDto.getUser().getId(), actualAuctionDto.getUser().getId());
        Assertions.assertEquals(expectedAuctionDto.getProduct().getId(), actualAuctionDto.getProduct().getId());
    }

    @Test
    public void convertToEntityTest() {
        // given
        int auctionId = 1;
        String title = "Test Auction";
        String description = "This is a test auction";
        double minPrice = 100.0;
        double maxPrice = 200.0;
        int minQuantity = 10;
        int maxQuantity = 20;
        LocalDateTime startDeliveryDate = LocalDateTime.of(2022, 12, 1, 10, 0, 0);
        LocalDateTime endDeliveryDate = LocalDateTime.of(2022, 12, 30, 10, 0, 0);
        LocalDateTime startDate = LocalDateTime.of(2022, 11, 1, 10, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2022, 11, 30, 10, 0, 0);
        AuctionState state = AuctionState.active;
        
        AuctionDto auctionDto = new AuctionDto();
        auctionDto.setId(auctionId);
        auctionDto.setTitle(title);
        auctionDto.setDescription(description);
        auctionDto.setMinPrice(minPrice);
        auctionDto.setMaxPrice(maxPrice);
        auctionDto.setMinQuantity(minQuantity);
        auctionDto.setMaxQuantity(maxQuantity);
        auctionDto.setStartDeliveryDate(startDeliveryDate);
        auctionDto.setEndDeliveryDate(endDeliveryDate);
        auctionDto.setStartDate(startDate);
        auctionDto.setEndDate(endDate);
        auctionDto.setState(state);
        auctionDto.setUser(userDto);
        auctionDto.setProduct(productDto);
        
        Auction expectedAuction = new Auction();
        expectedAuction.setId(0); // id gets not transformed
        expectedAuction.setTitle(title);
        expectedAuction.setDescription(description);
        expectedAuction.setMinPrice(minPrice);
        expectedAuction.setMaxPrice(maxPrice);
        expectedAuction.setMinQuantity(minQuantity);
        expectedAuction.setMaxQuantity(maxQuantity);
        expectedAuction.setStartDeliveryDate(startDeliveryDate);
        expectedAuction.setEndDeliveryDate(endDeliveryDate);
        expectedAuction.setStartDate(startDate);
        expectedAuction.setEndDate(endDate);
        expectedAuction.setState(state);
        expectedAuction.setUser(user);
        expectedAuction.setProduct(product);

        // when
        Auction actualAuction = auctionTransformer.convertToEntity(auctionDto);

        // assert
        Assertions.assertEquals(expectedAuction.getId(), actualAuction.getId());
        Assertions.assertEquals(expectedAuction.getTitle(), actualAuction.getTitle());
        Assertions.assertEquals(expectedAuction.getDescription(), actualAuction.getDescription());
        Assertions.assertEquals(expectedAuction.getMinPrice(), actualAuction.getMinPrice());
        Assertions.assertEquals(expectedAuction.getMaxPrice(), actualAuction.getMaxPrice());
        Assertions.assertEquals(expectedAuction.getMinQuantity(), actualAuction.getMinQuantity());
        Assertions.assertEquals(expectedAuction.getMaxQuantity(), actualAuction.getMaxQuantity());
        Assertions.assertEquals(expectedAuction.getStartDate(), actualAuction.getStartDate());
        Assertions.assertEquals(expectedAuction.getEndDate(), actualAuction.getEndDate());
        Assertions.assertEquals(expectedAuction.getStartDeliveryDate(), actualAuction.getStartDeliveryDate());
        Assertions.assertEquals(expectedAuction.getEndDeliveryDate(), actualAuction.getEndDeliveryDate());
        Assertions.assertEquals(expectedAuction.getState(), actualAuction.getState());
        Assertions.assertEquals(expectedAuction.getUser().getId(), actualAuction.getUser().getId());
        Assertions.assertEquals(expectedAuction.getProduct().getId(), actualAuction.getProduct().getId());
    }

    @Test
    public void repairEntityTest() {
        // given
        int auctionId = 1;
        String title = "Test Auction";
        String description = "This is a test auction";
        double minPrice = 100.0;
        double maxPrice = 200.0;
        int minQuantity = 10;
        int maxQuantity = 20;
        LocalDateTime startDeliveryDate = LocalDateTime.of(2022, 12, 1, 10, 0, 0);
        LocalDateTime endDeliveryDate = LocalDateTime.of(2022, 12, 30, 10, 0, 0);
        LocalDateTime startDate = LocalDateTime.of(2022, 11, 1, 10, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2022, 11, 30, 10, 0, 0);
        AuctionState state = AuctionState.active;

        User fakeUser = new User();
        fakeUser.setId(99);
        Product fakeProduct = new Product();
        fakeProduct.setId(99);

        Auction auction = new Auction();
        auction.setId(99);
        auction.setTitle(title);
        auction.setDescription(description);
        auction.setMinPrice(minPrice);
        auction.setMaxPrice(maxPrice);
        auction.setMinQuantity(minQuantity);
        auction.setMaxQuantity(maxQuantity);
        auction.setStartDeliveryDate(startDeliveryDate);
        auction.setEndDeliveryDate(endDeliveryDate);
        auction.setStartDate(startDate);
        auction.setEndDate(endDate);
        auction.setState(AuctionState.locked);
        auction.setUser(fakeUser);
        auction.setProduct(fakeProduct);

        Auction dbAuction = new Auction();
        dbAuction.setId(auctionId);
        dbAuction.setTitle(title);
        dbAuction.setDescription(description);
        dbAuction.setMinPrice(minPrice);
        dbAuction.setMaxPrice(maxPrice);
        dbAuction.setMinQuantity(minQuantity);
        dbAuction.setMaxQuantity(maxQuantity);
        dbAuction.setStartDeliveryDate(startDeliveryDate);
        dbAuction.setEndDeliveryDate(endDeliveryDate);
        dbAuction.setStartDate(startDate);
        dbAuction.setEndDate(endDate);
        dbAuction.setState(state);
        dbAuction.setUser(user);
        dbAuction.setProduct(product);

        // when
        Auction actualAuction = auctionTransformer.repairEntity(auction, dbAuction);

        // assert
        Assertions.assertEquals(dbAuction.getId(), actualAuction.getId());
        Assertions.assertEquals(dbAuction.getTitle(), actualAuction.getTitle());
        Assertions.assertEquals(dbAuction.getDescription(), actualAuction.getDescription());
        Assertions.assertEquals(dbAuction.getMinPrice(), actualAuction.getMinPrice());
        Assertions.assertEquals(dbAuction.getMaxPrice(), actualAuction.getMaxPrice());
        Assertions.assertEquals(dbAuction.getMinQuantity(), actualAuction.getMinQuantity());
        Assertions.assertEquals(dbAuction.getMaxQuantity(), actualAuction.getMaxQuantity());
        Assertions.assertEquals(dbAuction.getStartDate(), actualAuction.getStartDate());
        Assertions.assertEquals(dbAuction.getEndDate(), actualAuction.getEndDate());
        Assertions.assertEquals(dbAuction.getStartDeliveryDate(), actualAuction.getStartDeliveryDate());
        Assertions.assertEquals(dbAuction.getEndDeliveryDate(), actualAuction.getEndDeliveryDate());
        Assertions.assertEquals(dbAuction.getState(), actualAuction.getState());
        Assertions.assertEquals(dbAuction.getUser().getId(), actualAuction.getUser().getId());
        Assertions.assertEquals(dbAuction.getProduct().getId(), actualAuction.getProduct().getId());
    }
}
