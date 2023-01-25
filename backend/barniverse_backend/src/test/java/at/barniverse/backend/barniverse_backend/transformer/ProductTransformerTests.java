package at.barniverse.backend.barniverse_backend.transformer;

import at.barniverse.backend.barniverse_backend.dto.ProductDto;
import at.barniverse.backend.barniverse_backend.dto.ProductImageDto;
import at.barniverse.backend.barniverse_backend.enums.ProductState;
import at.barniverse.backend.barniverse_backend.model.Product;
import at.barniverse.backend.barniverse_backend.model.ProductImage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class ProductTransformerTests {

    @InjectMocks
    private ProductTransformer productTransformer;

    @Mock
    private ProductImageTransformer productImageTransformer;

    private List<ProductImage> productImages;
    private List<ProductImageDto> productImageDtos;

    @BeforeEach
    void setup() {
        ProductImage productImage1 = new ProductImage();
        productImage1.setId(1);
        ProductImage productImage2 = new ProductImage();
        productImage2.setId(2);
        ProductImageDto productImageDto1 = new ProductImageDto();
        productImageDto1.setId(1);
        ProductImageDto productImageDto2 = new ProductImageDto();
        productImageDto2.setId(2);

        productImages = new ArrayList<>();
        productImages.add(productImage1);
        productImages.add(productImage2);

        productImageDtos = new ArrayList<>();
        productImageDtos.add(productImageDto1);
        productImageDtos.add(productImageDto2);
    }

    @Test
    public void convertToDtoTest() {
        // given
        int productId = 1;
        String title = "Test Product";
        String description = "This is a test product";
        ProductState state = ProductState.active;

        Product product = new Product();
        product.setId(productId);
        product.setTitle(title);
        product.setDescription(description);
        product.setState(state);
        product.setImages(productImages);

        ProductDto expectedProductDto = new ProductDto();
        expectedProductDto.setId(productId);
        expectedProductDto.setTitle(title);
        expectedProductDto.setDescription(description);
        expectedProductDto.setState(state);
        expectedProductDto.setImages(productImageDtos);

        // when
        Mockito.when(productImageTransformer.convertToDto(any(ProductImage.class))).thenReturn(productImageDtos.get(0)).thenReturn(productImageDtos.get(1));

        ProductDto actualProductDto = productTransformer.convertToDto(product);

        // assert
        Assertions.assertEquals(expectedProductDto.getId(), actualProductDto.getId());
        Assertions.assertEquals(expectedProductDto.getTitle(), actualProductDto.getTitle());
        Assertions.assertEquals(expectedProductDto.getDescription(), actualProductDto.getDescription());
        Assertions.assertEquals(expectedProductDto.getState(), actualProductDto.getState());
        Assertions.assertEquals(expectedProductDto.getImages().size(), actualProductDto.getImages().size());
        Assertions.assertEquals(expectedProductDto.getImages().get(0).getId(), actualProductDto.getImages().get(0).getId());
        Assertions.assertEquals(expectedProductDto.getImages().get(1).getId(), actualProductDto.getImages().get(1).getId());
    }

    @Test
    public void convertToEntityTest() {
        // given
        int productId = 1;
        String title = "Test Product";
        String description = "This is a test product";
        ProductState state = ProductState.active;

        ProductDto productDto = new ProductDto();
        productDto.setId(productId);
        productDto.setTitle(title);
        productDto.setDescription(description);
        productDto.setState(state);
        productDto.setImages(productImageDtos);

        Product expectedProduct = new Product();
        expectedProduct.setId(0); // id gets set from database
        expectedProduct.setTitle(title);
        expectedProduct.setDescription(description);
        expectedProduct.setState(state);
        expectedProduct.setImages(productImages);

        // when
        Mockito.when(productImageTransformer.convertToEntity(any(ProductImageDto.class))).thenReturn(productImages.get(0)).thenReturn(productImages.get(1));

        Product actualProduct = productTransformer.convertToEntity(productDto);

        // assert
        Assertions.assertEquals(expectedProduct.getId(), actualProduct.getId());
        Assertions.assertEquals(expectedProduct.getTitle(), actualProduct.getTitle());
        Assertions.assertEquals(expectedProduct.getDescription(), actualProduct.getDescription());
        Assertions.assertEquals(expectedProduct.getState(), actualProduct.getState());
        Assertions.assertEquals(expectedProduct.getImages().size(), actualProduct.getImages().size());
        Assertions.assertEquals(expectedProduct.getImages().get(0).getId(), actualProduct.getImages().get(0).getId());
        Assertions.assertEquals(expectedProduct.getImages().get(1).getId(), actualProduct.getImages().get(1).getId());
    }

    @Test
    public void repairEntityTest() {
        // given
        int productId = 1;
        String title = "Test Product";
        String description = "This is a test product";
        ProductState state = ProductState.active;

        Product product = new Product();
        product.setId(99);
        product.setTitle(title);
        product.setDescription(description);
        product.setState(ProductState.deleted);
        product.setImages(productImages);

        Product dbProduct = new Product();
        dbProduct.setId(productId);
        dbProduct.setTitle(title);
        dbProduct.setDescription(description);
        dbProduct.setState(state);
        dbProduct.setImages(productImages);

        // when
        Product actualProduct = productTransformer.repairEntity(product, dbProduct);

        // assert
        Assertions.assertEquals(dbProduct.getId(), actualProduct.getId());
        Assertions.assertEquals(dbProduct.getTitle(), actualProduct.getTitle());
        Assertions.assertEquals(dbProduct.getDescription(), actualProduct.getDescription());
        Assertions.assertEquals(dbProduct.getState(), actualProduct.getState());
        Assertions.assertEquals(dbProduct.getImages().size(), actualProduct.getImages().size());
        Assertions.assertEquals(dbProduct.getImages().get(0).getId(), actualProduct.getImages().get(0).getId());
        Assertions.assertEquals(dbProduct.getImages().get(1).getId(), actualProduct.getImages().get(1).getId());
    }

}