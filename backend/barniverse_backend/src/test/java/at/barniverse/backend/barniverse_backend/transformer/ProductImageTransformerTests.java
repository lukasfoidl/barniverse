package at.barniverse.backend.barniverse_backend.transformer;

import at.barniverse.backend.barniverse_backend.dto.AuctionDto;
import at.barniverse.backend.barniverse_backend.dto.OfferDto;
import at.barniverse.backend.barniverse_backend.dto.ProductImageDto;
import at.barniverse.backend.barniverse_backend.dto.UserDto;
import at.barniverse.backend.barniverse_backend.enums.OfferState;
import at.barniverse.backend.barniverse_backend.model.Auction;
import at.barniverse.backend.barniverse_backend.model.Offer;
import at.barniverse.backend.barniverse_backend.model.ProductImage;
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
public class ProductImageTransformerTests {

    @InjectMocks private ProductImageTransformer productImageTransformer;

    @Test
    public void convertToDtoTest() {
        // given
        int productImageId = 1;
        String file = "Testdirectory/Testfile.jpg";

        ProductImage productImage = new ProductImage();
        productImage.setId(productImageId);
        productImage.setFile(file);

        ProductImageDto expectedProductImage = new ProductImageDto();
        expectedProductImage.setId(productImageId);
        expectedProductImage.setFile(file);

        // when
        ProductImageDto actualProductImageDto = productImageTransformer.convertToDto(productImage);

        // assert
        Assertions.assertEquals(expectedProductImage.getId(), actualProductImageDto.getId());
        Assertions.assertEquals(expectedProductImage.getFile(), actualProductImageDto.getFile());
    }

    @Test
    public void convertToEntityTest() {
        // given
        int productImageId = 1;
        String file = "Testdirectory/Testfile.jpg";

        ProductImageDto productImageDto = new ProductImageDto();
        productImageDto.setId(productImageId);
        productImageDto.setFile(file);

        ProductImage expectedProductImage = new ProductImage();
        expectedProductImage.setId(productImageId);
        expectedProductImage.setFile(file);

        // when
        ProductImage actualProductImage = productImageTransformer.convertToEntity(productImageDto);

        // assert
        Assertions.assertEquals(expectedProductImage.getId(), actualProductImage.getId());
        Assertions.assertEquals(expectedProductImage.getFile(), actualProductImage.getFile());
    }

    @Test
    public void repairEntityTest() {
        // given
        int productImageId = 1;
        String file = "Testdirectory/Testfile.jpg";

        ProductImage productImage = new ProductImage();
        productImage.setId(99);
        productImage.setFile(file);

        ProductImage dbProductImage = new ProductImage();
        dbProductImage.setId(0); // id gets set from database
        dbProductImage.setFile(file);

        // when
        ProductImage actualProductImage = productImageTransformer.repairEntity(productImage, dbProductImage);

        // assert
        Assertions.assertEquals(dbProductImage.getId(), actualProductImage.getId());
        Assertions.assertEquals(dbProductImage.getFile(), actualProductImage.getFile());
    }
}
