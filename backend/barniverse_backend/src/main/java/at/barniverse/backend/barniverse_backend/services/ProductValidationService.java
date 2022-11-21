package at.barniverse.backend.barniverse_backend.services;

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
        List<ProductImage> productImages = product.getImages();

        Optional<Product> dbProduct;
        try {
            dbProduct = productRepository.findById(product.getId());
        } catch (Exception exception) {
            return List.of(VALIDATION_ERROR);
        }
        // if product gets created dbProduct is empty, no validation of entity specific extras required
        // if product gets updated dbProduct already checked in ProductService
        if (dbProduct.isEmpty()) { return Collections.emptyList(); }

        List<ProductImage> dbProductImages = dbProduct.get().getImages();

        // check if product images are from product or new (need to be created)
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

        return errors;
    }
}
