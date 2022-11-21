package at.barniverse.backend.barniverse_backend.repository;

import at.barniverse.backend.barniverse_backend.model.Product;
import org.springframework.data.repository.CrudRepository;

/**
 * basic repository for product entity
 * will be auto implemented by Spring into a Bean called productRepository
 */
public interface ProductRepository extends CrudRepository<Product, Integer> {

}
