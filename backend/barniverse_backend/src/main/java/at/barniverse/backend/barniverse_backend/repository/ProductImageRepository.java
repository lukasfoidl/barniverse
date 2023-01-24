package at.barniverse.backend.barniverse_backend.repository;

import at.barniverse.backend.barniverse_backend.model.ProductImage;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * basic repository for product image entity
 */
public interface ProductImageRepository extends CrudRepository<ProductImage, Integer> {

}
