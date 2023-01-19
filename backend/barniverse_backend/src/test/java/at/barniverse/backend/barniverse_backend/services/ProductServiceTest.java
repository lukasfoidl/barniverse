package at.barniverse.backend.barniverse_backend.services;

import at.barniverse.backend.barniverse_backend.dto.AuctionDto;
import at.barniverse.backend.barniverse_backend.dto.ProductDto;
import at.barniverse.backend.barniverse_backend.dto.UserDto;
import at.barniverse.backend.barniverse_backend.enums.UserState;
import at.barniverse.backend.barniverse_backend.model.Auction;
import at.barniverse.backend.barniverse_backend.model.Product;
import at.barniverse.backend.barniverse_backend.model.User;
import at.barniverse.backend.barniverse_backend.repository.ProductRepository;
import at.barniverse.backend.barniverse_backend.security.JWTUtil;
import at.barniverse.backend.barniverse_backend.transformer.ProductTransformer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
    @Mock
    ProductRepository productRepository;
    @Mock
    ProductTransformer productTransformer;
    @Mock
    ProductValidationService productValidationService;
    @Mock
    JWTUtil jwtUtil;
    @InjectMocks
    ProductService productService;

    Auction auction;

    AuctionDto auctionDto;

    @BeforeEach
    void setup(){

        ProductDto productDto = new ProductDto();
        productDto.setId(1);
        productDto.setTitle("The best Gin");
        productDto.setDescription("Very good gin for a good party!");
        productDto.setImages(null);

        UserDto userDto = new UserDto();
        userDto.setId(11);
        userDto.setFirstname("Test");
        userDto.setLastname("Test");
        userDto.setUsername("JonnyDoe123");
        userDto.setEmail("jonnydoe@test.at");
        userDto.setPassword("JonnyDoe123");
        userDto.setState(UserState.active);

        auctionDto = new AuctionDto();
        auctionDto.setId(12);
        auctionDto.setDescription("Gin Auction");
        auctionDto.setEndDate(null);
        auctionDto.setEndDeliveryDate(null);
        auctionDto.setMaxPrice(10);
        auctionDto.setMinPrice(5);
        auctionDto.setMaxQuantity(100);
        auctionDto.setMinQuantity(100);
        auctionDto.setProduct(productDto);
        auctionDto.setLocked(false);
        auctionDto.setStartDate(null);
        auctionDto.setStartDeliveryDate(null);
        auctionDto.setTitle("the best Gin Auction");
        auctionDto.setUser(userDto);


        Product product = new Product();
        product.setId(1);
        product.setTitle("The best Gin");
        product.setDescription("Very good gin for a good party!");
        product.setImages(null);

        User user = new User();
        user.setId(11);
        user.setFirstname("Test");
        user.setLastname("Test");
        user.setUsername("JonnyDoe123");
        user.setEmail("jonnydoe@test.at");
        user.setPassword("JonnyDoe123");
        user.setState(UserState.active);


        auction = new Auction();
        auction.setId(12);
        auction.setDescription("Gin Auction");
        auction.setEndDate(null);
        auction.setEndDeliveryDate(null);
        auction.setMaxPrice(10);
        auction.setMinPrice(5);
        auction.setMaxQuantity(100);
        auction.setMinQuantity(100);
        auction.setProduct(product);
        auction.setLocked(false);
        auction.setStartDate(null);
        auction.setStartDeliveryDate(null);
        auction.setTitle("the best Gin Auction");
        auction.setUser(user);

    }

    @Test
    void addProduct() {



    }

    @Test
    void getProducts() {
    }

    @Test
    void getProduct() {
    }

    @Test
    void updateProduct() {
    }

    @Test
    void deleteProduct() {
    }

    @Test
    void deleteWithState() {
    }
}