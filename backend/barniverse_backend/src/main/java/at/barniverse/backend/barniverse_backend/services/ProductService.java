package at.barniverse.backend.barniverse_backend.services;

import at.barniverse.backend.barniverse_backend.exception.BarniverseException;
import at.barniverse.backend.barniverse_backend.dto.ProductDto;
import at.barniverse.backend.barniverse_backend.enums.ProductState;
import at.barniverse.backend.barniverse_backend.model.Product;
import at.barniverse.backend.barniverse_backend.model.ProductImage;
import at.barniverse.backend.barniverse_backend.repository.ProductRepository;
import at.barniverse.backend.barniverse_backend.transformer.ProductImageTransformer;
import at.barniverse.backend.barniverse_backend.transformer.ProductTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static at.barniverse.backend.barniverse_backend.configuration.Context.DATABASE_ERROR;

/**
 * service with product related functionality
 */
@Service
public class ProductService extends BaseService {

    @Autowired private ProductRepository productRepository;
    @Autowired private ProductTransformer productTransformer;
    @Autowired private ProductValidationService productValidationService;
    @Autowired private ProductImageTransformer productImageTransformer;
    @Autowired private ProductImageValidationService productImageValidationService;

    /**
     * add a product to the database
     * @param productDto dto which should be saved
     * @throws BarniverseException in case of failure which includes error messages
     */
    public void addProduct(ProductDto productDto) throws BarniverseException {
        productDto.setState(ProductState.active);

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

        validateWithSubEntitiesAndSaveParentEntity(productEntity);
    }

    /**
     * get all saved products from the database which do not have state deleted
     * @return loaded product dtos
     * @throws BarniverseException in case of failure which includes error messages
     */
    public List<ProductDto> getProducts() throws BarniverseException {
        List<Product> products;
        try {
            products = productRepository.findAllByState(ProductState.active);
        } catch (Exception exception) {
            throw new BarniverseException(List.of(DATABASE_ERROR), HttpStatus.INTERNAL_SERVER_ERROR, exception);
        }

        return convertToDto(productTransformer, products);
    }

    /**
     * update specific product in the database
     * @param productDto dto which should be updated (with id)
     * @throws BarniverseException in case of failure which includes error messages
     */
    public void updateProduct(ProductDto productDto) throws BarniverseException {
        // not with updateEntity because of the subentities
        Product product = getEntity(productRepository, productDto.getId());
        Product updatedProduct = productTransformer.convertToEntity(productDto);
        updatedProduct = productTransformer.repairEntity(updatedProduct, product);
        validateWithSubEntitiesAndSaveParentEntity(updatedProduct);
    }

    /**
     * deletes a product with state deleted
     * @param id id of the specific product
     * @throws BarniverseException in case of failure which includes error messages
     */
    public void deleteWithState(int id) throws BarniverseException {
        Product product = getEntity(productRepository, id);
        product.setState(ProductState.deleted);
        save(productRepository, product);
    }

    /**
     * extension method which validates a product and its subentities (product images) and saves the product
     * @param product product which should be validated and saved
     * @throws BarniverseException in case of failure which includes error messages
     */
    private void validateWithSubEntitiesAndSaveParentEntity(Product product) throws BarniverseException {
        List<String> errors = new ArrayList<>(productValidationService.validateEntityGetErrors(product));
        for (ProductImage child : product.getImages()) {
            errors.addAll(productImageValidationService.validateEntityGetErrors(child));
        }
        if (!errors.isEmpty()) {
            throw new BarniverseException(errors, HttpStatus.BAD_REQUEST, null);
        }
        save(productRepository, product);
    }

}
