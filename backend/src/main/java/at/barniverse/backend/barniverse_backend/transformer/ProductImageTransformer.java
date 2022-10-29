package at.barniverse.backend.barniverse_backend.transformer;

import at.barniverse.backend.barniverse_backend.dto.ProductImageDto;
import at.barniverse.backend.barniverse_backend.model.ProductImage;
import org.springframework.stereotype.Component;

// product image transformer for convertions between entity <-> dto
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
}
