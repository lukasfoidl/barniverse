package at.barniverse.backend.barniverse_backend.repository;

import at.barniverse.backend.barniverse_backend.model.ProductImage;
import org.springframework.data.repository.CrudRepository;

/**
 * basic repository for product image entity
 * will be auto implemented by Spring into a Bean called productImageRepository
 */
public interface ProductImageRepository extends CrudRepository<ProductImage, Integer> {

}
