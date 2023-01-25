package at.barniverse.backend.barniverse_backend.services;

import at.barniverse.backend.barniverse_backend.dto.*;
import at.barniverse.backend.barniverse_backend.enums.AuctionState;
import at.barniverse.backend.barniverse_backend.enums.OfferState;
import at.barniverse.backend.barniverse_backend.enums.UserState;
import at.barniverse.backend.barniverse_backend.exception.BarniverseException;
import at.barniverse.backend.barniverse_backend.model.*;
import at.barniverse.backend.barniverse_backend.repository.OfferRepository;
import at.barniverse.backend.barniverse_backend.transformer.OfferTransformer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
class OfferServiceTest {
    @Mock
    OfferRepository offerRepository;
    @Mock
    OfferValidationService offerValidationService;
    @Mock
    OfferTransformer offerTransformer;

    @InjectMocks
    OfferService offerService;

    Offer offer;
    OfferDto offerDto;
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
    void addOffer() throws BarniverseException {
    given(offerTransformer.convertToEntity(offerDto)).willReturn(offer);

    offerService.addOffer(offerDto);

    verify(offerRepository, times(1)).save(offer);
    }

    @Test
    void getOffersFromAuction() throws BarniverseException {

        given(offerRepository.findAllByAuction_Id(12)).willReturn(offerList);
        given(offerTransformer.convertToDto(offer)).willReturn(offerDto);

        List <OfferDto> newList = offerService.getOffersFromAuction(12);

        System.out.println(offerDtoList.get(0).getId());
        System.out.println(newList.get(0).getId());
        System.out.println(offerDtoList.get(0).getAuction().getId());
        System.out.println(newList.get(0).getAuction().getId());

        assertEquals(offerDtoList.size(), newList.size());
        assertEquals(offerDtoList.get(0).getId(), newList.get(0).getId());
    }

    @Test
    void getOffersFromUser() throws BarniverseException {
        given(offerRepository.findAllByUser_Id(12)).willReturn(offerList);
        given(offerTransformer.convertToDto(offer)).willReturn(offerDto);

        List <OfferDto> newList = offerService.getOffersFromUser(12);

        System.out.println(offerDtoList.get(0).getId());
        System.out.println(newList.get(0).getId());
        System.out.println(offerDtoList.get(0).getAuction().getId());
        System.out.println(newList.get(0).getAuction().getId());
        System.out.println(offerDtoList.get(0).getAuction().getProduct().getDescription());
        System.out.println(newList.get(0).getAuction().getProduct().getDescription());

        assertEquals(offerDtoList.size(), newList.size());
        assertEquals(offerDtoList.get(0).getId(), newList.get(0).getId());
        assertEquals(offerDtoList.get(0).getAuction().getProduct().getDescription(), newList.get(0).getAuction().getProduct().getDescription());
        verify(offerRepository, times(1)).findAllByUser_Id(12);
    }

    @Test
    void acceptOffer() throws BarniverseException {
        given(offerRepository.findById(offer.getId())).willReturn(Optional.of(offer));
        given(offerRepository.findAllByAuction_Id(offer.getAuction().getId())).willReturn(offerList);

        offerService.acceptOffer(1);

        verify(offerRepository, times(1)).findById(offer.getId());
        verify(offerRepository, times(1)).findAllByAuction_Id(offer.getAuction().getId());
        verify(offerRepository, times(1)).saveAll(offerList);

    }
}