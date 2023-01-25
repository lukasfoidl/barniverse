package at.barniverse.backend.barniverse_backend.services;

import at.barniverse.backend.barniverse_backend.dto.*;
import at.barniverse.backend.barniverse_backend.enums.AuctionState;
import at.barniverse.backend.barniverse_backend.enums.OfferState;
import at.barniverse.backend.barniverse_backend.enums.UserState;
import at.barniverse.backend.barniverse_backend.exception.BarniverseException;
import at.barniverse.backend.barniverse_backend.model.*;
import at.barniverse.backend.barniverse_backend.repository.AuctionRepository;
import at.barniverse.backend.barniverse_backend.repository.OfferRepository;
import at.barniverse.backend.barniverse_backend.repository.ProductRepository;
import at.barniverse.backend.barniverse_backend.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class OfferValidationServiceTest {
    @Mock
    UserRepository userRepository;
    @Mock
    AuctionRepository auctionRepository;
    @Mock
    OfferRepository offerRepository;

    @InjectMocks
    OfferValidationService offerValidationService;

    OfferDto offerDto;
    Offer offer;

    List<Offer> offerList;
    List<OfferDto> offerDtoList;

    @BeforeEach
    void setUp(){
        UserDto userDto = new UserDto();
        userDto.setId(11);
        userDto.setFirstname("Test");
        userDto.setLastname("Test");
        userDto.setUsername("JonnyDoe123");
        userDto.setEmail("jonnydoe@test.at");
        userDto.setPassword("JonnyDoe123");
        userDto.setState(UserState.active);


        ProductImageDto imageDto = new ProductImageDto();
        imageDto.setId(1);
        imageDto.setFile("test1.jpeg");

        ProductImage image = new ProductImage();
        image.setId(1);
        image.setFile("test1.jpeg");

        List<ProductImageDto> imagesDto= new ArrayList<>();
        imagesDto.add(imageDto);
        List<ProductImage> images= new ArrayList<>();
        images.add(image);

        ProductDto productDto = new ProductDto();
        productDto.setId(1);
        productDto.setTitle("The best Gin");
        productDto.setDescription("Very good gin for a good party!");
        productDto.setImages(imagesDto);

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

        LocalDateTime date = LocalDateTime.now();

        Auction auction = new Auction();
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


        AuctionDto auctionDto = new AuctionDto();
        auctionDto.setId(12);
        auctionDto.setDescription("Gin Auction");
        auctionDto.setEndDate(date);
        auctionDto.setEndDeliveryDate(date);
        auctionDto.setMaxPrice(10);
        auctionDto.setMinPrice(5);
        auctionDto.setMaxQuantity(100);
        auctionDto.setMinQuantity(100);
        auctionDto.setProduct(productDto);
        auctionDto.setState(AuctionState.active);
        auctionDto.setStartDate(date);
        auctionDto.setStartDeliveryDate(date);
        auctionDto.setTitle("the best Gin Auction");
        auctionDto.setUser(userDto);




        offer = new Offer();
        offer.setId(1);
        offer.setAuction(auction);
        offer.setDeliveryDate(LocalDateTime.of(2023,05, 20, 16, 00 ));
        offer.setUser(user);
        offer.setState(OfferState.running);
        offer.setPrice(50.20);
        offer.setQuantity(5);

        offerDto = new OfferDto();
        offerDto.setId(1);
        offerDto.setAuction(auctionDto);
        offerDto.setDeliveryDate(LocalDateTime.of(2023,05, 20, 16, 00 ));
        offerDto.setUser(userDto);
        offerDto.setState(OfferState.running);
        offerDto.setPrice(50.20);
        offerDto.setQuantity(5);

        offerList= new ArrayList<>();
        offerList.add(offer);

        offerDtoList = new ArrayList<>();
        offerDtoList.add(offerDto);




    }

    @Test
    void validateEntitySpecificExtras() throws BarniverseException {

        List<String> error = new ArrayList<>();
        error.add("Auction is not active!");
        error.add("Auction is not running");
        error.add("no offers can be placed!");

        given(offerRepository.existsByIdAndState(offer.getId(), OfferState.running)).willReturn(true);
        given(userRepository.existsById(offer.getUser().getId())).willReturn(true);
        given(userRepository.existsByIdAndState(offer.getUser().getId(), UserState.active)).willReturn(true);
        given(auctionRepository.existsById(offer.getAuction().getId())).willReturn(true);
        //given(auctionRepository.existsByIdAndStartDateBeforeAndEndDateAfter(offer.getAuction().getId(), LocalDateTime.now(), LocalDateTime.now())).willReturn(true);

        List<String> errorNew = offerValidationService.validateEntitySpecificExtras(offer);

        assertEquals(error.toString(), errorNew.toString());



    }

    @Test
    void validateEntitySpecificExtrasFail() throws BarniverseException {

        List<String> error = new ArrayList<>();
        error.add("Auction is not active!");
        error.add("Auction is not running");
        error.add("no offers can be placed!");

        given(offerRepository.existsByIdAndState(offer.getId(), OfferState.running)).willReturn(true);
        given(userRepository.existsById(offer.getUser().getId())).willReturn(true);
        given(userRepository.existsByIdAndState(offer.getUser().getId(), UserState.active)).willReturn(true);
        given(auctionRepository.existsById(offer.getAuction().getId())).willReturn(true);


        List<String> errorNew = offerValidationService.validateEntitySpecificExtras(offer);


        System.out.println(error.toString());
        System.out.println(errorNew.toString());
        assertEquals(error.toString(), errorNew.toString());



    }

    @Test
    void validateEntitySpecificExtrasFail2() throws BarniverseException {

        List<String> error = new ArrayList<>();
        error.add("Auction not found!");

        given(offerRepository.existsByIdAndState(offer.getId(), OfferState.running)).willReturn(true);
        given(userRepository.existsById(offer.getUser().getId())).willReturn(true);
        given(userRepository.existsByIdAndState(offer.getUser().getId(), UserState.active)).willReturn(true);
        given(auctionRepository.existsById(offer.getAuction().getId())).willReturn(false);

        List<String> errorNew = offerValidationService.validateEntitySpecificExtras(offer);

        System.out.println(error.toString());
        System.out.println(errorNew.toString());
        assertEquals(error.toString(), errorNew.toString());



    }

    @Test
    void validateEntitySpecificExtrasFail3() throws BarniverseException {

        List<String> error = new ArrayList<>();
        error.add("User is not active!");
        error.add("Auction is not active!");
        error.add("Auction is not running");
        error.add("no offers can be placed!");
        given(offerRepository.existsByIdAndState(offer.getId(), OfferState.running)).willReturn(true);
        given(userRepository.existsById(offer.getUser().getId())).willReturn(true);
        given(userRepository.existsByIdAndState(offer.getUser().getId(), UserState.active)).willReturn(false);
        given(auctionRepository.existsById(offer.getAuction().getId())).willReturn(true);

        List<String> errorNew = offerValidationService.validateEntitySpecificExtras(offer);

        System.out.println(error.toString());
        System.out.println(errorNew.toString());
        assertEquals(error.toString(), errorNew.toString());



    }
}