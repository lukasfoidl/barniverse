package at.barniverse.backend.barniverse_backend.services;

import at.barniverse.backend.barniverse_backend.model.Auction;
import at.barniverse.backend.barniverse_backend.model.Product;
import at.barniverse.backend.barniverse_backend.model.ProductImage;
import at.barniverse.backend.barniverse_backend.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static at.barniverse.backend.barniverse_backend.configuration.Context.VALIDATION_ERROR;

/**
 * validation service which validates product specific extras
 */
@Service
public class ProductValidationService extends ValidationService<Product> {

    @Autowired
    private ProductRepository productRepository;

    /**
     * validates product specific extras
     * @param product entity which should be validated
     * @return error messages, empty if validation was successful
     */
    @Override
    public List<String> validateEntitySpecificExtras(Product product) {
        List<String> errors = new ArrayList<>();
        boolean isPOST = product.getId() == 0;

        try {
            Optional<Product> dbProduct = productRepository.findById(product.getId());
            errors = validateProduct(errors, dbProduct, isPOST); // validate entity itself
            errors = validateProductImages(errors, product, dbProduct, isPOST); // validate foreign key product images
        } catch (Exception exception) {
            return List.of(VALIDATION_ERROR);
        }

        return errors;
    }

    private List<String> validateProduct(List<String> errors, Optional<Product> dbProduct, boolean isPOST) throws Exception {
        if (!isPOST) { // PUT
            // do not update inactive product, on POST always set to active
            if (!dbProduct.get().isActive()) { // existence already checked before validation
                errors.add("Product is not active!");
            }
        }
        return errors;
    }

    private List<String> validateProductImages(List<String> errors, Product product, Optional<Product> dbProduct, boolean isPOST) throws Exception {
        if (!isPOST) { // PUT
            List<ProductImage> productImages = product.getImages();
            List<ProductImage> dbProductImages = dbProduct.get().getImages(); // existence already checked before validation

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
