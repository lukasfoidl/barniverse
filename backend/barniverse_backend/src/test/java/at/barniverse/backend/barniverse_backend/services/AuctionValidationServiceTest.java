package at.barniverse.backend.barniverse_backend.services;

import at.barniverse.backend.barniverse_backend.enums.AuctionState;
import at.barniverse.backend.barniverse_backend.enums.UserState;
import at.barniverse.backend.barniverse_backend.exception.BarniverseException;
import at.barniverse.backend.barniverse_backend.model.Auction;
import at.barniverse.backend.barniverse_backend.model.Product;
import at.barniverse.backend.barniverse_backend.model.ProductImage;
import at.barniverse.backend.barniverse_backend.model.User;
import at.barniverse.backend.barniverse_backend.repository.AuctionRepository;
import at.barniverse.backend.barniverse_backend.repository.ProductRepository;
import at.barniverse.backend.barniverse_backend.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class AuctionValidationServiceTest {

    @Mock
    UserRepository userRepository;
    @Mock
    ProductRepository productRepository;
    @Mock
    AuctionRepository auctionRepository;
    @InjectMocks
    AuctionValidationService auctionValidationService;

    Auction auction;

    Auction auctionWrongEndDate;

    @BeforeEach
    void setUp(){
        ProductImage image = new ProductImage();
        image.setId(1);
        image.setFile("test1.jpeg");

        List<ProductImage> images= new ArrayList<>();
        images.add(image);

        Product product = new Product();
        product.setId(1);
        product.setTitle("The best Gin");
        product.setDescription("Very good gin for a good party!");
        product.setImages(images);

        User user = new User();
        user.setId(11);
        user.setFirstname("Test");
        user.setLastname("Test");
        user.setUsername("JonnyDoe123");
        user.setEmail("jonnydoe@test.at");
        user.setPassword("JonnyDoe123");
        user.setState(UserState.active);

        LocalDateTime date = LocalDateTime.of(2023,05, 20, 18, 00 );

        LocalDateTime dateWrong = LocalDateTime.of(2022,05, 20, 18, 00 );

        auction = new Auction();
        auction.setId(12);
        auction.setDescription("Gin Auction");
        auction.setEndDate(date);
        auction.setEndDeliveryDate(date);
        auction.setMaxPrice(10);
        auction.setMinPrice(5);
        auction.setMaxQuantity(100);
        auction.setMinQuantity(100);
        auction.setProduct(product);
        auction.setState(AuctionState.active);
        auction.setStartDate(date);
        auction.setStartDeliveryDate(date);
        auction.setTitle("the best Gin Auction");
        auction.setUser(user);

        auctionWrongEndDate = new Auction();
        auctionWrongEndDate.setId(12);
        auctionWrongEndDate.setDescription("Gin Auction");
        auctionWrongEndDate.setEndDate(dateWrong);
        auctionWrongEndDate.setEndDeliveryDate(date);
        auctionWrongEndDate.setMaxPrice(10);
        auctionWrongEndDate.setMinPrice(5);
        auctionWrongEndDate.setMaxQuantity(100);
        auctionWrongEndDate.setMinQuantity(100);
        auctionWrongEndDate.setProduct(product);
        auctionWrongEndDate.setState(AuctionState.active);
        auctionWrongEndDate.setStartDate(date);
        auctionWrongEndDate.setStartDeliveryDate(date);
        auctionWrongEndDate.setTitle("the best Gin Auction");
        auctionWrongEndDate.setUser(user);

    }
    @Test
    void validateEntitySpecificExtras() throws BarniverseException {
        List<String> errors = new ArrayList<>();
        errors.add("Auction already started, no changes possible!");
        errors.add("User is not active!");

        given(auctionRepository.existsByIdAndState(auction.getId(), AuctionState.active)).willReturn(true);
        given(userRepository.existsById(auction.getUser().getId())).willReturn(true);


        List<String> errorsNew = auctionValidationService.validateEntitySpecificExtras(auction);

        System.out.println(errors.toString());
        System.out.println(errorsNew.toString());

        assertEquals(errors.toString(), errorsNew.toString());


    }

    @Test
    void validateTaskToggleAuction() {
        BarniverseException thrown = Assertions.assertThrows(BarniverseException.class, () -> {
            auctionValidationService.validateTaskToggleAuction(auctionWrongEndDate);
        });

    }
}