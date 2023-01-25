package at.barniverse.backend.barniverse_backend.controller;

import at.barniverse.backend.barniverse_backend.configuration.Context;
import at.barniverse.backend.barniverse_backend.dto.ProductDto;
import at.barniverse.backend.barniverse_backend.dto.ProductImageDto;
import at.barniverse.backend.barniverse_backend.enums.ProductState;

import at.barniverse.backend.barniverse_backend.services.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

 @Mock
 ProductService service;

 @InjectMocks
 ProductController productController;

 MockMvc mockMvc;

 @BeforeEach
 void setup(){
     mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
 }





    @Test
    @DisplayName("add Product API")
    void addProduct() throws Exception {

        List<ProductImageDto> list = new ArrayList<ProductImageDto>();

        ProductImageDto image = new ProductImageDto();
        image.setFile("empty.jpeg");
        image.setId(10);
        list.add(image);

        ProductDto productDto = new ProductDto();
        productDto.setId(10);
        productDto.setDescription("Tequilla Bro");
        productDto.setImages(list);
        productDto.setTitle("Tequilla");
        productDto.setState(ProductState.active);

        ObjectMapper objectmapper = new ObjectMapper();
        String json = objectmapper.writeValueAsString(productDto);

        ResponseEntity entity = new ResponseEntity(null, HttpStatus.OK);

        mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk());

    }

    @Test
    @DisplayName("Get Products API")
    void getProducts() throws Exception {
        List<ProductImageDto> list = new ArrayList<ProductImageDto>();

        ProductImageDto image = new ProductImageDto();
        image.setFile("empty.jpeg");
        image.setId(10);
        list.add(image);

        ProductDto productDto = new ProductDto();
        productDto.setId(10);
        productDto.setDescription("Tequilla Bro");
        productDto.setImages(list);
        productDto.setTitle("Tequilla");
        productDto.setState(ProductState.active);

        List<ProductDto> listProducts = new ArrayList<>();
        listProducts.add(productDto);

        ObjectMapper objectmapper = new ObjectMapper();
        String json = objectmapper.writeValueAsString(listProducts);

        ResponseEntity entity = new ResponseEntity(json, HttpStatus.OK);

        Mockito.when(service.getProducts()).thenReturn(listProducts);

        mockMvc.perform(get("/api/products").accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(json));

    }

    @Test
    @DisplayName("Update Product API")
    void updateProduct() throws Exception {
        List<ProductImageDto> list = new ArrayList<ProductImageDto>();

        ProductImageDto image = new ProductImageDto();
        image.setFile("empty.jpeg");
        image.setId(10);
        list.add(image);

        ProductDto productDto = new ProductDto();
        productDto.setId(10);
        productDto.setDescription("Tequilla der runter geht!");
        productDto.setImages(list);
        productDto.setTitle("Tequilla");
        productDto.setState(ProductState.active);

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(productDto);

        mockMvc.perform(put("/api/products").contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isOk());
    }

    @Test
    @DisplayName("Delete Product API")
    void deleteProduct() throws Exception {

     ResponseEntity entity = new ResponseEntity(HttpStatus.OK);

        //Mockito.when(service.deleteProduct(10)).thenReturn(entity);

     mockMvc.perform(delete("/api/products/10"))
             .andDo(print())
             .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName(" Product Delete with state API")
    void deleteWithState() throws Exception {

        mockMvc.perform(put("/api/products/10/deleteWithState"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Prduct delete with wrong Id API")
    void deleteWithStateWithWrongId() throws Exception {

        mockMvc.perform(put("/api/products/x/deleteWithState/"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }


}