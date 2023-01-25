package at.barniverse.backend.barniverse_backend.services;

import at.barniverse.backend.barniverse_backend.dto.ProductDto;
import at.barniverse.backend.barniverse_backend.dto.ProductImageDto;
import at.barniverse.backend.barniverse_backend.enums.ProductState;
import at.barniverse.backend.barniverse_backend.exception.BarniverseException;
import at.barniverse.backend.barniverse_backend.model.Product;
import at.barniverse.backend.barniverse_backend.model.ProductImage;
import at.barniverse.backend.barniverse_backend.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
@ExtendWith(MockitoExtension.class)
class ProductValidationServiceTest {

    @Mock
    ProductRepository productRepository;

    @InjectMocks
    ProductValidationService productValidationService;
    Product product;

    ProductDto productDto;

    Product productWithError;

    ProductImage image;

    ProductImageDto imageDto;

    ProductImage imageWithError;

    @BeforeEach
    void setUp(){
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

        imageWithError = new ProductImage();
        imageWithError.setId(0);
        imageWithError.setFile("test1.jpeg");
        List<ProductImage> productImageList = new ArrayList<>();
        productImageList.add(imageWithError);

        productWithError = new Product();
        productWithError.setId(1);
        productWithError.setTitle("The best Gin");
        productWithError.setDescription("Very good gin for a good party!");
        productWithError.setImages(productImageList);
    }

    @Test
    void validateEntitySpecificExtras() throws BarniverseException {
        List<String> errorListEmpty = new ArrayList<>();

        given(productRepository.existsByIdAndState(product.getId(), ProductState.active)).willReturn(true);
        given(productRepository.findById(product.getId())).willReturn(Optional.of(product));

        List<String> newList = productValidationService.validateEntitySpecificExtras(product);

        assertEquals(newList , errorListEmpty);
    }

    @Test
    void validateEntitySpecificExtrasExceptionThrown() throws BarniverseException {
        List<String> errorListEmpty = new ArrayList<>();

        given(productRepository.existsByIdAndState(productWithError.getId(), ProductState.deleted)).willReturn(false);
        given(productRepository.findById(product.getId())).willReturn(Optional.of(product));

        BarniverseException thrown = Assertions.assertThrows(BarniverseException.class, () -> {
            List<String> newList = productValidationService.validateEntitySpecificExtras(productWithError);
        });

       assertEquals("[The transaction has been refused! Validation could not be finished!]", thrown.getErrorMessages().toString());




    }
}