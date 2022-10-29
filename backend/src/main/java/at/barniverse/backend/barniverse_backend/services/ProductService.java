package at.barniverse.backend.barniverse_backend.services;

import at.barniverse.backend.barniverse_backend.dto.ProductDto;
import at.barniverse.backend.barniverse_backend.model.IEntity;
import at.barniverse.backend.barniverse_backend.model.Product;
import at.barniverse.backend.barniverse_backend.repository.ProductRepository;
import at.barniverse.backend.barniverse_backend.transformer.ProductTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

// service with product related functionality
@Service
public class ProductService extends BaseService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductTransformer productTransformer;

    // create new product (not with addEntity in BaseService because of sub entities)
    public ResponseEntity<Object> addProduct(ProductDto productDto) {
        Product product = productTransformer.convertToEntity(productDto);
        return validateAndSafeEntities(productRepository, getEntityListFromProduct(product), product);
    }

    // get all products
    public ResponseEntity<Object> getProducts() {
        return getEntities(productRepository, productTransformer);
    }

    // get specific product
    public ResponseEntity<Object> getProduct(int id) {
        return getEntity(productRepository, productTransformer, id);
    }

    // update specific product
//    public ResponseEntity<Object> updateProduct(ProductDto productDto) {
//        ResponseEntity<Object> response = existsRecord(productRepository, productDto.getId());
//        if (response.getStatusCode() != HttpStatus.OK) { return response; }
//    }

    // delete specific product
    public ResponseEntity<Object> deleteProduct(int id) {
        return deleteEntity(productRepository, id);
    }

    // extension method which puts all sub entities in a list together with the parent entity
    private List<IEntity> getEntityListFromProduct(Product product) {
        List<IEntity> entities = new ArrayList<>();
        entities.add(product);
        entities.addAll(product.getImages());
        return entities;
    }
}
