package at.barniverse.backend.barniverse_backend.services;

import at.barniverse.backend.barniverse_backend.model.ProductImage;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

// validation service which validates entity specific extras (like foreign keys, subentities, etc.)
@Service
public class ProductImageValidationService extends ValidationService<ProductImage> {

    @Override
    public List<String> validateEntitySpecificExtras(ProductImage productImage) {
        return Collections.emptyList();
    }
}
