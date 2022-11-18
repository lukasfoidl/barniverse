package at.barniverse.backend.barniverse_backend.transformer;

import at.barniverse.backend.barniverse_backend.dto.ProductImageDto;
import at.barniverse.backend.barniverse_backend.model.ProductImage;
import org.springframework.stereotype.Component;

/**
 * transforms product image entities and dtos, <br>
 * EXCEPTION TO OTHER TRANSFORMERS: <br>
 * repairEntity clears the id (set to 0) and does not set the id from database (because of List characteristic), <br>
 * necessary for create (POST), <br>
 * convertToEntity does not clear the id but takes the id from dto, <br>
 * necessary for update (PUT)
 */
@Component
public class ProductImageTransformer implements ITransformer<ProductImage, ProductImageDto> {

    /**
     * transforms product image entity to product image dto
     * @param productImage entity which should be transformed
     * @return product image dto
     */
    @Override
    public ProductImageDto convertToDto(ProductImage productImage) {
        ProductImageDto productImageDto = new ProductImageDto();

        productImageDto.setId(productImage.getId());
        productImageDto.setFile(productImage.getFile());

        return productImageDto;
    }

    /**
     * transforms product image dto to product image entity, <br>
     * id property DOES GET transformed from dto to entity, in case of update (PUT)
     * @param productImageDto dto which should be transformed
     * @return product image entity
     */
    @Override
    public ProductImage convertToEntity(ProductImageDto productImageDto) {
        ProductImage productImage = new ProductImage();

        productImage.setId(productImageDto.getId());
        productImage.setFile(productImageDto.getFile());

        return productImage;
    }

    /**
     * repairs product image entity in case of create (POST), <br>
     * id gets set to 0 to create new entity
     * @param productImage entity which needs to be repaired
     * @param dbProductImage entity with the missing data
     * @return repaired entity
     */
    @Override
    public ProductImage repairEntity(ProductImage productImage, ProductImage dbProductImage) {
        productImage.setId(0); // set id to 0 to create new entity
        return productImage;
    }
}
