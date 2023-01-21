package at.barniverse.backend.barniverse_backend.controller;

import at.barniverse.backend.barniverse_backend.dto.ProductDto;
import at.barniverse.backend.barniverse_backend.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static at.barniverse.backend.barniverse_backend.configuration.Context.CORS_ORIGINS;

/**
 * controller with basic CRUD routing for product related URLs,
 * also handles product images in product context (no extra controller for product images necessary)
 */
@RestController
@CrossOrigin(origins = CORS_ORIGINS)
@RequestMapping(path = "/api")
public class ProductController {

    @Autowired
    private ProductService productService;

    /**
     * add a product to the database
     * @param productDto object sent from the client
     * @return response with corresponding status code and error message in case of failure
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping(path="/products")
    public ResponseEntity<Object> addProduct(@RequestBody ProductDto productDto) throws Exception {
        return productService.addProduct(productDto);
    }

    /**
     * get all saved products from the database which do not have state deleted
     * @return response with corresponding status code and loaded product dtos or error message in case of failure
     */
    @GetMapping(path="/products")
    public ResponseEntity<Object> getProducts() {
        return productService.getProducts();
    }

    /**
     * get specific product from the database
     * @param id id of the specific product
     * @return response with corresponding status code and loaded product dto or error message in case of failure
     */
    @GetMapping(path="/products/{id}")
    public ResponseEntity<Object> getProduct(@PathVariable int id) {
        return productService.getProduct(id);
    }

    //TODO: Safety alert! Products can be updated only with Id.
    /**
     * update specific product in the database
     * @param productDto object sent from the client (with id)
     * @return response with corresponding status code and error message in case of failure
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PutMapping(path="/products")
    public ResponseEntity<Object> updateProduct(@RequestBody ProductDto productDto) {
        return productService.updateProduct(productDto);
    }

    //TODO: Safety alert! Products can be deleted only with Id.
    /**
     * delete specific product from the database
     * @param id id of the specific product
     * @return response with corresponding status code and error message in case of failure
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @DeleteMapping(path="/products/{id}")
    public ResponseEntity<Object> deleteProduct(@PathVariable int id) {
        return productService.deleteProduct(id);
    }

    /**
     * deletes a product with state deleted
     * @param id id of the specific product
     * @return response with corresponding status code and error message in case of failure
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PutMapping(path="/products/deleteWithState/{id}")
    public ResponseEntity<Object> deleteWithState(@PathVariable int id) {
        return productService.deleteWithState(id);
    }
}
