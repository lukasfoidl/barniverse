package at.barniverse.backend.barniverse_backend.repository;

import at.barniverse.backend.barniverse_backend.enums.ProductState;
import at.barniverse.backend.barniverse_backend.enums.UserState;
import at.barniverse.backend.barniverse_backend.model.Product;
import at.barniverse.backend.barniverse_backend.model.ProductImage;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

/**
 * basic repository for product entity
 * will be auto implemented by Spring into a Bean called productRepository
 */
public interface ProductRepository extends CrudRepository<Product, Integer> {

    boolean existsById(int id);
    boolean existsByIdAndState(int id, ProductState state);

    List<Product> findAllByState(ProductState state);

}
