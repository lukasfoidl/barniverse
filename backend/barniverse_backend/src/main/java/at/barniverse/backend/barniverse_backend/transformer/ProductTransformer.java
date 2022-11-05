package at.barniverse.backend.barniverse_backend.transformer;

import at.barniverse.backend.barniverse_backend.dto.ProductDto;
import at.barniverse.backend.barniverse_backend.dto.ProductImageDto;
import at.barniverse.backend.barniverse_backend.model.Product;
import at.barniverse.backend.barniverse_backend.model.ProductImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

// product transformer for convertions between entity <-> dto
@Component
public class ProductTransformer implements ITransformer<Product, ProductDto> {

    @Autowired
    private ProductImageTransformer productImageTransformer;

    @Override
    public ProductDto convertToDto(Product product) {
        ProductDto productDto = new ProductDto();

        productDto.setId(product.getId());
        productDto.setName((product.getName()));
        productDto.setDescription(product.getDescription());
        productDto.setImages(convertImagesToDto(product.getImages()));

        return productDto;
    }

    @Override
    public Product convertToEntity(ProductDto productDto) {
        Product product = new Product();

        // id gets set from database
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setImages(convertImagesToEntity(productDto.getImages()));

        return product;
    }

    // repair entity in case of update (PUT)
    @Override
    public Product repairEntity(Product product, Product dbProduct) {
       product.setId(dbProduct.getId()); // set id to update existing entity
       return product;
    }

    // extension method which converts sub entities to dtos
    private List<ProductImageDto> convertImagesToDto(List<ProductImage> productImages) {
        List<ProductImageDto> productImageDtos = new ArrayList<>();
        if (productImages != null) {
            productImages.forEach(productImage -> {
                productImageDtos.add(productImageTransformer.convertToDto(productImage));
            });
        }
        return productImageDtos;
    }

    // extension method which converts sub dtos to entities
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
