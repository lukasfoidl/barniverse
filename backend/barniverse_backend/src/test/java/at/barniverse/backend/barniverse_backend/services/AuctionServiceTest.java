package at.barniverse.backend.barniverse_backend.services;

import at.barniverse.backend.barniverse_backend.dto.AuctionDto;
import at.barniverse.backend.barniverse_backend.dto.ProductDto;
import at.barniverse.backend.barniverse_backend.dto.ProductImageDto;
import at.barniverse.backend.barniverse_backend.dto.UserDto;
import at.barniverse.backend.barniverse_backend.enums.AuctionState;
import at.barniverse.backend.barniverse_backend.enums.UserState;
import at.barniverse.backend.barniverse_backend.exception.BarniverseException;
import at.barniverse.backend.barniverse_backend.model.Auction;
import at.barniverse.backend.barniverse_backend.model.Product;
import at.barniverse.backend.barniverse_backend.model.ProductImage;
import at.barniverse.backend.barniverse_backend.model.User;
import at.barniverse.backend.barniverse_backend.repository.AuctionRepository;
import at.barniverse.backend.barniverse_backend.transformer.AuctionTransformer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.awt.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AuctionServiceTest {


    @Mock
    AuctionRepository auctionRepository;
    @Mock
    AuctionTransformer auctionTransformer;
    @Mock
    AuctionValidationService auctionValidationService ;

    @InjectMocks
    AuctionService auctionService;

    AuctionDto auctionDto;
    Auction auction;

    List <Auction> list = new ArrayList<>();
    List <AuctionDto> listDto = new ArrayList<>();


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


        auctionDto = new AuctionDto();
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

        list = new ArrayList<>();
        list.add(auction);
        listDto = new ArrayList<>();
        list.add(auction);

    }

    @Test
    void addAuction() throws BarniverseException {

        auctionService.addAuction(auctionDto);

        verify(auctionRepository, times(1)).save(null);
    }

    @Test
    void getNotClosedAuctions() throws BarniverseException {
        given(auctionRepository.findAll()).willReturn(list);
        given(auctionTransformer.convertToDto(auction)).willReturn(auctionDto);

        List<AuctionDto> newList = auctionService.getAuctions();

        System.out.println(list.get(0).getDescription());
        System.out.println(newList.get(0).getDescription());

        assertEquals(list.get(0).getDescription(), newList.get(0).getDescription());
    }


    @Test
    void getUnlockedAuctions() throws BarniverseException {
        given(auctionRepository.findAllByState(AuctionState.active)).willReturn(list);
        given(auctionTransformer.convertToDto(auction)).willReturn(auctionDto);

        List<AuctionDto> newList = auctionService.getUnlockedAuctions();

        System.out.println(list.get(0).getDescription());
        System.out.println(newList.get(0).getDescription());

        assertEquals(list.get(0).getDescription(), newList.get(0).getDescription());
    }

    @Test
    void getMyAuctions() throws BarniverseException {
        given(auctionRepository.findAllByUserId(12)).willReturn(list);
        given(auctionTransformer.convertToDto(auction)).willReturn(auctionDto);

        List<AuctionDto> newList = auctionService.getMyAuctions(12);

        System.out.println(list.get(0).getDescription());
        System.out.println(newList.get(0).getDescription());

        //2 times because of base service same name
        verify(auctionTransformer, times(2)).convertToDto(auction);
        assertEquals(list.get(0).getDescription(), newList.get(0).getDescription());

    }

    @Test
    void updateAuction() throws BarniverseException {
        given(auctionRepository.findById(auction.getId())).willReturn(Optional.of(auction));
        given(auctionTransformer.convertToEntity(auctionDto)).willReturn(auction);
        given(auctionTransformer.repairEntity(any(Auction.class), any(Auction.class))).willReturn(auction);

        auctionService.updateAuction(auctionDto);

        verify(auctionRepository, times(1)).save(auction);

    }

    @Test
    void toggleState() throws BarniverseException {

        given(auctionRepository.findById(12)).willReturn(Optional.of(auction));
        AuctionState state =auctionService.toggleState(12);

        verify(auctionValidationService, times(1)).validateTaskToggleAuction(auction);

        assertEquals(AuctionState.locked, state);

    }
}