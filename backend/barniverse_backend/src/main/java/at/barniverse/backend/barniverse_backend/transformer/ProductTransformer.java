package at.barniverse.backend.barniverse_backend.transformer;

import at.barniverse.backend.barniverse_backend.dto.ProductDto;
import at.barniverse.backend.barniverse_backend.dto.ProductImageDto;
import at.barniverse.backend.barniverse_backend.model.Product;
import at.barniverse.backend.barniverse_backend.model.ProductImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * transforms product entities and dtos
 */
@Component
public class ProductTransformer implements ITransformer<Product, ProductDto> {

    @Autowired
    private ProductImageTransformer productImageTransformer;

    /**
     * transforms product entity to product dto, <br>
     * product image property of entity will be transformed to a product image dto by ProductImageTransformer
     * @param product entity which should be transformed
     * @return product dto
     */
    @Override
    public ProductDto convertToDto(Product product) {
        ProductDto productDto = new ProductDto();

        productDto.setId(product.getId());
        productDto.setName((product.getName()));
        productDto.setDescription(product.getDescription());
        productDto.setImages(convertImagesToDto(product.getImages()));

        return productDto;
    }

    /**
     * transforms product dto to product entity, <br>
     * id property does NOT get transformed from dto to entity, because id gets set from the database automatically, <br>
     * product image property of dto will be transformed to a product image entity by ProductImageTransformer
     * @param productDto dto which should be transformed
     * @return product entity
     */
    @Override
    public Product convertToEntity(ProductDto productDto) {
        Product product = new Product();

        // id gets set from database
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setImages(convertImagesToEntity(productDto.getImages()));

        return product;
    }

    /**
     * repairs product entity after transformation in case of update (PUT), <br>
     * id gets set to update entity
     * @param product entity which needs to be repaired
     * @param dbProduct entity with the missing data
     * @return repaired entity
     */
    @Override
    public Product repairEntity(Product product, Product dbProduct) {
       product.setId(dbProduct.getId()); // set id to update existing entity
       return product;
    }

    /**
     * extension method which converts sub entities (product images) to dtos by ProductImageTransformer
     * @param productImages product image entities which should be transformed
     * @return product image dtos
     */
    private List<ProductImageDto> convertImagesToDto(List<ProductImage> productImages) {
        List<ProductImageDto> productImageDtos = new ArrayList<>();
        if (productImages != null) {
            productImages.forEach(productImage -> {
                productImageDtos.add(productImageTransformer.convertToDto(productImage));
            });
        }
        return productImageDtos;
    }

    /**
     * extension method which converts sub dtos (product image dtos) to entities by ProductImageTransformer
     * @param productImageDtos product image dtos which should be transformed
     * @return product image entities
     */
    private List<ProductImage> convertImagesToEntity(List<ProductImageDto> productImageDtos) {
        List<ProductImage> productImages = new ArrayList<>();
        if (productImageDtos != null) {
            productImageDtos.forEach(productImageDto -> {
                productImages.add(productImageTransformer.convertToEntity(productImageDto));
            });
        }
        return productImages;
    }

}
