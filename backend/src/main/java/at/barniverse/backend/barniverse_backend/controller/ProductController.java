package at.barniverse.backend.barniverse_backend.controller;

import at.barniverse.backend.barniverse_backend.dto.ProductDto;
import at.barniverse.backend.barniverse_backend.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// product controller with basic CRUD routing (handles product images in product context as well)
@RestController
@RequestMapping(path = "/api")
public class ProductController {

    @Autowired
    private ProductService productService;

    // create new product
    @PostMapping(path="/products")
    public ResponseEntity<Object> addProduct(@RequestBody ProductDto productDto) {
        return productService.addProduct(productDto);
    }

    // get all products
    @GetMapping(path="/products")
    public ResponseEntity<Object> getProducts() {
        return productService.getProducts();
    }

    // get specific product
    @GetMapping(path="/products/{id}")
    public ResponseEntity<Object> getProduct(@PathVariable int id) {
        return productService.getProduct(id);
    }

    // update specific product
    //TODO: Safety alert! Products can be updated only with Id.
    @PutMapping(path="/products")
    public ResponseEntity<Object> updateProduct(@RequestBody ProductDto productDto) {
//        return productService.updateProduct(productDto);
        return new ResponseEntity<>(null, HttpStatus.NOT_IMPLEMENTED);
    }

    // delete specific product
    //TODO: Safety alert! Products can be deleted only with Id.
    @DeleteMapping(path="/products/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable int id) {
        return productService.deleteProduct(id);
    }
}
