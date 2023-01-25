package at.barniverse.backend.barniverse_backend.repository;

import at.barniverse.backend.barniverse_backend.enums.ProductState;
import at.barniverse.backend.barniverse_backend.model.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * basic repository for product entity
 */
public interface ProductRepository extends CrudRepository<Product, Integer> {

    /**
     * check if product with a specific id exists
     * @param id id of the specific product
     * @return true if product exists, otherwise false
     */
    boolean existsById(int id);

    /**
     * check if product with a specific id and a specific state exists
     * @param id id of the specific product
     * @param state state which should be filtered
     * @return true if product exists, otherwise false
     */
    boolean existsByIdAndState(int id, ProductState state);

    /**
     * get all products with a specific state
     * @param state state which should be filtered
     * @return products with the given state
     */
    List<Product> findAllByState(ProductState state);

    /**
     * get the number of products with a specific state
     * @param state state which should be filtered
     * @return number of found products, 0 if no products found
     */
    long countAllByState(ProductState state);
}
