package at.barniverse.backend.barniverse_backend.controller;

import at.barniverse.backend.barniverse_backend.exception.BarniverseException;
import at.barniverse.backend.barniverse_backend.dto.ProductDto;
import at.barniverse.backend.barniverse_backend.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static at.barniverse.backend.barniverse_backend.configuration.Context.CORS_ORIGINS;

/**
 * controller with basic CRUD routing for product related URLs,
 * also handles product images in product context
 */
@RestController
@CrossOrigin(origins = CORS_ORIGINS)
@RequestMapping(path = "/api")
public class ProductController {

    @Autowired private ProductService productService;

    /**
     * add a product to the database, <br>
     * reserved for role admin
     * @param productDto object sent from the client
     * @return response with corresponding status code
     * @throws BarniverseException in case of failure which includes error messages
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping(path="/products")
    public ResponseEntity<Object> addProduct(@RequestBody ProductDto productDto) throws BarniverseException {
        productService.addProduct(productDto);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    /**
     * get all saved products from the database which do not have state deleted
     * @return response with corresponding status code and loaded product dtos
     * @throws BarniverseException in case of failure which includes error messages
     */
    @GetMapping(path="/products")
    public ResponseEntity<Object> getProducts() throws BarniverseException {
        List<ProductDto> results = productService.getProducts();
        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    //TODO: Safety alert! Products can be updated only with Id.
    /**
     * update specific product in the database, <br>
     * reserved for role admin
     * @param productDto object sent from the client (with id)
     * @return response with corresponding status code
     * @throws BarniverseException in case of failure which includes error messages
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PutMapping(path="/products")
    public ResponseEntity<Object> updateProduct(@RequestBody ProductDto productDto) throws BarniverseException {
        productService.updateProduct(productDto);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    /**
     * deletes a product with state deleted, <br>
     * reserved for role admin
     * @param id id of the specific product
     * @return response with corresponding status code
     * @throws BarniverseException in case of failure which includes error messages
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PutMapping(path="/products/{id}/deleteWithState")
    public ResponseEntity<Object> deleteWithState(@PathVariable int id) throws BarniverseException {
        productService.deleteWithState(id);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
