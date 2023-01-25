package at.barniverse.backend.barniverse_backend.services;

import at.barniverse.backend.barniverse_backend.dto.AuctionDto;
import at.barniverse.backend.barniverse_backend.dto.ProductDto;
import at.barniverse.backend.barniverse_backend.dto.ProductImageDto;
import at.barniverse.backend.barniverse_backend.dto.UserDto;
import at.barniverse.backend.barniverse_backend.enums.ProductState;
import at.barniverse.backend.barniverse_backend.enums.UserState;
import at.barniverse.backend.barniverse_backend.exception.BarniverseException;
import at.barniverse.backend.barniverse_backend.model.Auction;
import at.barniverse.backend.barniverse_backend.model.Product;
import at.barniverse.backend.barniverse_backend.model.ProductImage;
import at.barniverse.backend.barniverse_backend.model.User;
import at.barniverse.backend.barniverse_backend.repository.ProductRepository;
import at.barniverse.backend.barniverse_backend.security.JWTUtil;
import at.barniverse.backend.barniverse_backend.transformer.ProductImageTransformer;
import at.barniverse.backend.barniverse_backend.transformer.ProductTransformer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
    @Mock
    ProductRepository productRepository;
    @Mock
    ProductTransformer productTransformer;

    @Mock
    ProductImageValidationService productImageValidationService;

    @Mock
    ProductImageTransformer productImageTransformer;
    @Mock
    ProductValidationService productValidationService;
    @Mock
    JWTUtil jwtUtil;
    @InjectMocks
    ProductService productService;

    Product product;

    ProductDto productDto;

    ProductImage image;

    ProductImageDto imageDto;

    List<Product> listProduct;

    List<ProductDto> listProductDto;

    @BeforeEach
    void setup(){


        UserDto userDto = new UserDto();
        userDto.setId(11);
        userDto.setFirstname("Test");
        userDto.setLastname("Test");
        userDto.setUsername("JonnyDoe123");
        userDto.setEmail("jonnydoe@test.at");
        userDto.setPassword("JonnyDoe123");
        userDto.setState(UserState.active);

        AuctionDto auctionDto = new AuctionDto();
        auctionDto.setId(12);
        auctionDto.setDescription("Gin Auction");
        auctionDto.setEndDate(null);
        auctionDto.setEndDeliveryDate(null);
        auctionDto.setMaxPrice(10);
        auctionDto.setMinPrice(5);
        auctionDto.setMaxQuantity(100);
        auctionDto.setMinQuantity(100);
        auctionDto.setProduct(productDto);
        //auctionDto.setLocked(false);
        auctionDto.setStartDate(null);
        auctionDto.setStartDeliveryDate(null);
        auctionDto.setTitle("the best Gin Auction");
        auctionDto.setUser(userDto);

        imageDto = new ProductImageDto();
        imageDto.setId(1);
        imageDto.setFile("test1.jpeg");

        image = new ProductImage();
        image.setId(1);
        image.setFile("test1.jpeg");

        List<ProductImageDto> imagesDto= new ArrayList<>();
        imagesDto.add(imageDto);
        List<ProductImage> images= new ArrayList<>();
        images.add(image);

        productDto = new ProductDto();
        productDto.setId(1);
        productDto.setTitle("The best Gin");
        productDto.setDescription("Very good gin for a good party!");
        productDto.setImages(imagesDto);

        product = new Product();
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

        Auction auction = new Auction();
        auction.setId(12);
        auction.setDescription("Gin Auction");
        auction.setEndDate(null);
        auction.setEndDeliveryDate(null);
        auction.setMaxPrice(10);
        auction.setMinPrice(5);
        auction.setMaxQuantity(100);
        auction.setMinQuantity(100);
        auction.setProduct(product);
        //auction.setLocked(false);
        auction.setStartDate(null);
        auction.setStartDeliveryDate(null);
        auction.setTitle("the best Gin Auction");
        auction.setUser(user);

        listProduct = new ArrayList<>();
        listProduct.add(product);

        listProductDto = new ArrayList<>();
        listProductDto.add(productDto);

    }

    @Test
    void addProduct() throws BarniverseException {
        List<String> errorList = new ArrayList<>();
        given(productTransformer.convertToEntity(productDto)).willReturn(product);
        given(productValidationService.validateEntityGetErrors(product)).willReturn(errorList);
        productService.addProduct(productDto);
        verify(productRepository, times(1)).save(product);
    }

    @Test
    void getProducts() throws BarniverseException {

        given(productRepository.findAllByState(ProductState.active)).willReturn(listProduct);
        given(productTransformer.convertToDto(product)).willReturn(productDto);

        List<ProductDto> list = productService.getProducts();

        System.out.println(listProductDto.size());
        System.out.println(list.size());

        System.out.println(listProductDto.get(0).getDescription());
        System.out.println(list.get(0).getDescription());

        assertEquals(listProductDto, list);


    }

    @Test
    void getProduct() {
    }

    @Test
    void updateProduct() throws BarniverseException {

        List<String> errorList = new ArrayList<>();


        given(productRepository.findById(1)).willReturn(Optional.of(product));
        given(productTransformer.convertToEntity(productDto)).willReturn(product);
        given(productTransformer.repairEntity(product, product)).willReturn(product);
        given(productValidationService.validateEntityGetErrors(product)).willReturn(errorList);
        given(productImageValidationService.validateEntityGetErrors(image)).willReturn(errorList);

        productService.updateProduct(productDto);

        verify(productRepository, times(1)).findById(1);
        verify(productRepository, times(1)).save(product);


    }


    @Test
    void deleteWithState() throws BarniverseException {
        given(productRepository.findById(1)).willReturn(Optional.of(product));
        productService.deleteWithState(1);
        verify(productRepository, times(1)).findById(1);
        verify(productRepository, times(1)).save(product);

    }
}