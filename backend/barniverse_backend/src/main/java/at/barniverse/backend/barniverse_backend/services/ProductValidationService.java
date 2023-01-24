package at.barniverse.backend.barniverse_backend.services;

import at.barniverse.backend.barniverse_backend.exception.BarniverseException;
import at.barniverse.backend.barniverse_backend.enums.ProductState;
import at.barniverse.backend.barniverse_backend.model.Product;
import at.barniverse.backend.barniverse_backend.model.ProductImage;
import at.barniverse.backend.barniverse_backend.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static at.barniverse.backend.barniverse_backend.configuration.Context.VALIDATION_ERROR;

/**
 * validation service with product validation functionality
 */
@Service
public class ProductValidationService extends ValidationService<Product> {

    @Autowired
    private ProductRepository productRepository;

    /**
     * validates product specific extras
     * @param product entity which should be validated
     * @return error messages, empty if validation was successful
     * @throws BarniverseException in case of failure which includes error messages
     */
    @Override
    public List<String> validateEntitySpecificExtras(Product product) throws BarniverseException {
        List<String> errors = new ArrayList<>();
        boolean isPOST = product.getId() == 0;

        try {
            errors = validateProduct(errors, product, isPOST); // validate entity itself
            errors = validateProductImages(errors, product, isPOST); // validate foreign key product images
        } catch (Exception exception) {
            throw new BarniverseException(List.of(VALIDATION_ERROR), HttpStatus.INTERNAL_SERVER_ERROR, exception);
        }

        return errors;
    }

    /**
     * extension method which validates product itself
     * @param errors messages of errors which already arisen
     * @param product product which should be validated
     * @param isPOST true if validation happens in context of a POST request, otherwise false
     * @return messages of already arisen errors as well as new error messages
     */
    private List<String> validateProduct(List<String> errors, Product product, boolean isPOST) {
        if (!isPOST) { // PUT (existence already checked in getEntity())
            // do not update inactive product, on POST always set to active
            if (!productRepository.existsByIdAndState(product.getId(), ProductState.active)) {
                errors.add("Product is not active!");
            }
        }
        return errors;
    }

    /**
     * extension method which validates product image property of a product
     * @param errors messages of errors which already arisen
     * @param product product of which the product images should be validated
     * @param isPOST true if validation happens in context of a POST request, otherwise false
     * @return messages of already arisen errors as well as new error messages
     */
    private List<String> validateProductImages(List<String> errors, Product product, boolean isPOST) {
        if (!isPOST) { // PUT (existence already checked in getEntity())
            List<ProductImage> productImages = product.getImages();
            List<ProductImage> dbProductImages = productRepository.findById(product.getId()).get().getImages();

            // check if product images are from product or new (need to be created)
            // on POST all images new
            boolean imageRelatedToProduct = false;
            for (ProductImage productImage : productImages) {
                if (productImage.getId() == 0) {
                    break;
                }
                for (ProductImage dbProductImage : dbProductImages) {
                    if (productImage.getId() == dbProductImage.getId()) {
                        imageRelatedToProduct = true;
                        break;
                    }
                }
                if (!imageRelatedToProduct) {
                    errors.add("Image not found!");
                }
                imageRelatedToProduct = false;
            }
        }
        return errors;
    }
}
