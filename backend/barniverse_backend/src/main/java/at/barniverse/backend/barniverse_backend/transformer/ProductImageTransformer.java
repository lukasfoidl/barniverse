package at.barniverse.backend.barniverse_backend.transformer;

import at.barniverse.backend.barniverse_backend.dto.ProductImageDto;
import at.barniverse.backend.barniverse_backend.model.ProductImage;
import org.springframework.stereotype.Component;

// product image transformer for convertions between entity <-> dto
// !! DIFFERENCE TO OTHER TRANSFORMER !!
// repairEntity clears the id and does not set the id from database (because of List characteristic)
// convertToEntity does not clear the id but takes the id from dto
// (additional id check in ProductValidationService to ensure relation between image and product)
@Component
public class ProductImageTransformer implements ITransformer<ProductImage, ProductImageDto> {

    @Override
    public ProductImageDto convertToDto(ProductImage productImage) {
        ProductImageDto productImageDto = new ProductImageDto();

        productImageDto.setId(productImage.getId());
        productImageDto.setFile(productImage.getFile());

        return productImageDto;
    }

    @Override
    public ProductImage convertToEntity(ProductImageDto productImageDto) {
        ProductImage productImage = new ProductImage();

        productImage.setId(productImageDto.getId());
        productImage.setFile(productImageDto.getFile());

        return productImage;
    }

    // repair entity in case of create (POST)
    @Override
    public ProductImage repairEntity(ProductImage productImage, ProductImage dbProductImage) {
        productImage.setId(0); // set id to 0 to create new entity
        return productImage;
    }
}
