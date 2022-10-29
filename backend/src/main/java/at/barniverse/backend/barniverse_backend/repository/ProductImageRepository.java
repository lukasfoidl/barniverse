package at.barniverse.backend.barniverse_backend.repository;

import at.barniverse.backend.barniverse_backend.model.ProductImage;
import org.springframework.data.repository.CrudRepository;

// This will be AUTO IMPLEMENTED by Spring into a Bean called productImageRepository
// CRUD refers Create, Read, Update, Delete

public interface ProductImageRepository extends CrudRepository<ProductImage, Integer> {

}
