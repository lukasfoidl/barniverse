package at.barniverse.backend.barniverse_backend.services;

import at.barniverse.backend.barniverse_backend.dto.ProductDto;
import at.barniverse.backend.barniverse_backend.model.Product;
import at.barniverse.backend.barniverse_backend.model.ProductImage;
import at.barniverse.backend.barniverse_backend.repository.ProductRepository;
import at.barniverse.backend.barniverse_backend.transformer.ProductImageTransformer;
import at.barniverse.backend.barniverse_backend.transformer.ProductTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static at.barniverse.backend.barniverse_backend.configuration.Context.DATABASE_ERROR;
import static at.barniverse.backend.barniverse_backend.configuration.Context.INVALID_ID;

/**
 * service with product related functionality
 */
@Service
public class ProductService extends BaseService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductTransformer productTransformer;

    @Autowired
    private ProductValidationService productValidationService;

    @Autowired
    private ProductImageTransformer productImageTransformer;

    @Autowired
    private ProductImageValidationService productImageValidationService;

    /**
     * add a product to the database
     * @param productDto dto which should be saved
     * @return response with corresponding status code and error message in case of failure
     */
    public ResponseEntity<Object> addProduct(ProductDto productDto) {
        // not with addEntity because of the subentities
        Product productEntity = productTransformer.convertToEntity(productDto);

        // repair images at create (POST) and not like standard at update (PUT)
        // because repairEntity function does the opposite in ProductImageTransformer in comparison to the other transformers
        List<ProductImage> productImages = productEntity.getImages();
        List<ProductImage> repairedProductImages = new ArrayList<>();
        productImages.forEach(productImage -> {
            repairedProductImages.add(productImageTransformer.repairEntity(productImage, null));
        });
        productEntity.setImages(repairedProductImages);

        return validateWithSubentitiesAndSaveEntity(
                productRepository, productValidationService, productEntity, productImageValidationService, productEntity.getImages()
        );
    }

    /**
     * get all saved products from the database
     * @return response with corresponding status code and loaded product dtos or error message in case of failure
     */
    public ResponseEntity<Object> getProducts() {
        return getEntities(productRepository, productTransformer);
    }

    /**
     * get specific product from the database
     * @param id id of the specific product
     * @return response with corresponding status code and loaded product dto or error message in case of failure
     */
    public ResponseEntity<Object> getProduct(int id) {
        return getEntityAsDto(productRepository, productTransformer, id);
    }

    /**
     * update specific product in the database
     * @param productDto dto which should be updated (with id)
     * @return response with corresponding status code and error message in case of failure
     */
    public ResponseEntity<Object> updateProduct(ProductDto productDto) {
        // not with updateEntity because of the subentities
        Optional<Product> dbEntity;
        try {
            dbEntity = productRepository.findById(productDto.getId());
        } catch (Exception exception) {
            return new ResponseEntity<>(DATABASE_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (dbEntity.isEmpty() || dbEntity.get().getId() != productDto.getId()) {
            return new ResponseEntity<>(INVALID_ID, HttpStatus.BAD_REQUEST);
        }
        Product updatedProduct = productTransformer.convertToEntity(productDto);
        updatedProduct = productTransformer.repairEntity(updatedProduct, dbEntity.get());
        return validateWithSubentitiesAndSaveEntity(
                productRepository, productValidationService, updatedProduct, productImageValidationService, updatedProduct.getImages()
        );
    }

    /**
     * delete specific product from the database
     * @param id id of the specific product
     * @return response with corresponding status code and error message in case of failure
     */
    public ResponseEntity<Object> deleteProduct(int id) {
        return deleteEntity(productRepository, id);
    }

}
