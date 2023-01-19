package at.barniverse.backend.barniverse_backend.controller;

import at.barniverse.backend.barniverse_backend.configuration.Context;
import at.barniverse.backend.barniverse_backend.dto.ProductDto;
import at.barniverse.backend.barniverse_backend.dto.ProductImageDto;
import at.barniverse.backend.barniverse_backend.enums.ProductState;

import at.barniverse.backend.barniverse_backend.services.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
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

        ResponseEntity entity = new ResponseEntity(json, HttpStatus.OK);

        Mockito.when(service.addProduct(any(ProductDto.class))).thenReturn(entity);


        mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk());

    }

    @Test
    void getProducts() throws Exception {
        String json = "[{\"id\":1,\"title\":\"Gin\",\"description\":\"Eine 1 Liter Flasche bester Gin aus dem Jahr 2020\",\"state\":\"active\",\"images\":[{\"id\":1,\"file\":\"Gin01.jpeg\"},{\"id\":2,\"file\":\"Gin02.jpg\"},{\"id\":3,\"file\":\"Gin03.jpg\"}]},{\"id\":2,\"title\":\"Gruener Veltliner\",\"description\":\"Bester Wein aus dem Burgenland (0.75 l)\",\"state\":\"active\",\"images\":[{\"id\":4,\"file\":\"Wein01.jpeg\"}]},{\"id\":3,\"title\":\"Berliner Luft\",\"description\":\"1 Flasche (1 l) Berliner Luft Pfefferminzlikoer aus der Metropole Deutschlands\",\"state\":\"active\",\"images\":[{\"id\":5,\"file\":\"BerlinerLuft01.jpg\"},{\"id\":6,\"file\":\"BerlinerLuft02.jpg\"}]},{\"id\":4,\"title\":\"Goesser Bier\",\"description\":\"Goesser Bier 0,75 l mit 3 Volumsprozent aus dem Herzen Oesterreichs\",\"state\":\"active\",\"images\":[{\"id\":7,\"file\":\"Bier01.jpg\"}]},{\"id\":5,\"title\":\"Obstler\",\"description\":\"Schnaps der brutal runterbrennt, 0,75l, 40%, VORSICHT: nicht in hohen Mengen zu sich nehmen\",\"state\":\"active\",\"images\":[]},{\"id\":7,\"title\":\"Wine\",\"description\":\"Good and Tasty Wine\",\"state\":\"active\",\"images\":[]}]";

        ResponseEntity entity = new ResponseEntity(json, HttpStatus.OK);

        Mockito.when(service.getProducts()).thenReturn(entity);

        mockMvc.perform(get("/api/products").accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(json));

    }

    //Gets Product by id = 1
    @Test
    void getProduct() throws Exception {
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

        ResponseEntity entity = new ResponseEntity(json, HttpStatus.OK);

        Mockito.when(service.getProduct(10)).thenReturn(entity);

        mockMvc.perform(get("/api/products/10").accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(json));

    }

    @Test
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

        ResponseEntity entity = new ResponseEntity(json, HttpStatus.OK);

        Mockito.when(service.updateProduct(any(ProductDto.class))).thenReturn(entity);

        mockMvc.perform(put("/api/products").contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isOk());
    }

    @Test
    void deleteProduct() throws Exception {

     ResponseEntity entity = new ResponseEntity(HttpStatus.OK);

     Mockito.when(service.deleteProduct(10)).thenReturn(entity);

     mockMvc.perform(delete("/api/products/10"))
             .andDo(print())
             .andExpect(status().isOk());
    }

    @Test
    void deleteWithState() throws Exception {
        String json = "{\"message\":\"deletedWithState\"}";
        ResponseEntity entity = new ResponseEntity(json, HttpStatus.OK);
        Mockito.when(service.deleteWithState(10)).thenReturn(entity);

        mockMvc.perform(put("/api/products/deleteWithState/10"))
                .andDo(print())
                .andExpect(status().isOk());


    }

    @Test
    void deleteWithStateIdNotFound() throws Exception {

        ResponseEntity entity = new ResponseEntity(Context.INVALID_ID, HttpStatus.BAD_REQUEST);
        Mockito.when(service.deleteWithState(123)).thenReturn(entity);

        mockMvc.perform(put("/api/products/deleteWithState/123"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(Context.INVALID_ID));


    }

    @Test
    void deleteWithStateInternalServerError() throws Exception {

        ResponseEntity entity = new ResponseEntity(Context.DATABASE_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
        Mockito.when(service.deleteWithState(12)).thenReturn(entity);

        mockMvc.perform(put("/api/products/deleteWithState/12"))
                .andDo(print())
                .andExpect(status().isInternalServerError())
                .andExpect(content().string(Context.DATABASE_ERROR));


    }
}