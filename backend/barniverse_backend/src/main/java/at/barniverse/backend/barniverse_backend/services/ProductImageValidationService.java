package at.barniverse.backend.barniverse_backend.services;

import at.barniverse.backend.barniverse_backend.model.ProductImage;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * validation service which validates product image specific extras
 */
@Service
public class ProductImageValidationService extends ValidationService<ProductImage> {

    /**
     * validates product image specific extras
     * @param productImage entity which should be validated
     * @return error messages, empty if validation was successful
     */
    @Override
    public List<String> validateEntitySpecificExtras(ProductImage productImage) {
        return Collections.emptyList();
    }
}
