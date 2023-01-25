package at.barniverse.backend.barniverse_backend.controller;

import at.barniverse.backend.barniverse_backend.dto.AuctionDto;
import at.barniverse.backend.barniverse_backend.dto.ProductDto;
import at.barniverse.backend.barniverse_backend.dto.UserDto;
import at.barniverse.backend.barniverse_backend.enums.AuctionState;
import at.barniverse.backend.barniverse_backend.enums.UserState;
import at.barniverse.backend.barniverse_backend.services.AuctionService;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class AuctionControllerTest {

    @Mock
    AuctionService service;
    @InjectMocks
    AuctionController auctionController;

    MockMvc mockMvc;

    @BeforeEach
    void setup(){
        mockMvc = MockMvcBuilders.standaloneSetup(auctionController).build();

    }

    @Test
    void addAuction() throws Exception {

        ProductDto product = new ProductDto();
        product.setId(1);
        product.setTitle("The best Gin");
        product.setDescription("Very good gin for a good party!");
        product.setImages(null);

        UserDto user = new UserDto();
        user.setId(11);
        user.setFirstname("Test");
        user.setLastname("Test");
        user.setUsername("JonnyDoe123");
        user.setEmail("jonnydoe@test.at");
        user.setPassword("JonnyDoe123");
        user.setState(UserState.active);


        AuctionDto dto = new AuctionDto();
        dto.setDescription("Gin Auction");
        dto.setEndDate(null);
        dto.setEndDeliveryDate(null);
        dto.setMaxPrice(10);
        dto.setMinPrice(5);
        dto.setMaxQuantity(100);
        dto.setMinQuantity(100);
        dto.setProduct(product);
        dto.setStartDate(null);
        dto.setStartDeliveryDate(null);
        dto.setTitle("the best Gin Auction");
        dto.setUser(user);

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(dto);

        mockMvc.perform(post("/api/auctions").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk());

    }

    @Test
    void getNotClosedActiveAuctions() throws Exception {

        ProductDto product = new ProductDto();
        product.setId(1);
        product.setTitle("The best Gin");
        product.setDescription("Very good gin for a good party!");
        product.setImages(null);

        UserDto user = new UserDto();
        user.setId(11);
        user.setFirstname("Test");
        user.setLastname("Test");
        user.setUsername("JonnyDoe123");
        user.setEmail("jonnydoe@test.at");
        user.setPassword("JonnyDoe123");
        user.setState(UserState.active);

        AuctionDto dto = new AuctionDto();
        dto.setId(1);
        dto.setDescription("Gin Auction");
        dto.setEndDate(null);
        dto.setEndDeliveryDate(null);
        dto.setMaxPrice(10);
        dto.setMinPrice(5);
        dto.setMaxQuantity(100);
        dto.setMinQuantity(100);
        dto.setProduct(product);
        //dto.setLocked(false);
        dto.setStartDate(null);
        dto.setStartDeliveryDate(null);
        dto.setTitle("the best Gin Auction");
        dto.setUser(user);

        AuctionDto dto2 = new AuctionDto();
        dto2.setId(2);
        dto2.setDescription("Gin Auction");
        dto2.setEndDate(null);
        dto2.setEndDeliveryDate(null);
        dto2.setMaxPrice(10);
        dto2.setMinPrice(5);
        dto2.setMaxQuantity(100);
        dto2.setMinQuantity(100);
        dto2.setProduct(product);
        //dto2.setLocked(false);
        dto2.setStartDate(null);
        dto2.setStartDeliveryDate(null);
        dto2.setTitle("the best Gin Auction");
        dto2.setUser(user);


        List<AuctionDto> auctionDtos = new ArrayList<>();
        auctionDtos.add(dto);
        auctionDtos.add(dto2);

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(auctionDtos);

        ResponseEntity entity = new ResponseEntity(json, HttpStatus.OK);

        Mockito.when(service.getNotClosedActiveAuctions()).thenReturn(auctionDtos);

        mockMvc.perform(get("/api/auctions").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(json));
    }


    @Test
    void getNotClosedAuctions() throws Exception {

        ProductDto product = new ProductDto();
        product.setId(1);
        product.setTitle("The best Gin");
        product.setDescription("Very good gin for a good party!");
        product.setImages(null);

        UserDto user = new UserDto();
        user.setId(11);
        user.setFirstname("Test");
        user.setLastname("Test");
        user.setUsername("JonnyDoe123");
        user.setEmail("jonnydoe@test.at");
        user.setPassword("JonnyDoe123");
        user.setState(UserState.active);

        AuctionDto dto = new AuctionDto();
        dto.setId(1);
        dto.setDescription("Gin Auction");
        dto.setEndDate(null);
        dto.setEndDeliveryDate(null);
        dto.setMaxPrice(10);
        dto.setMinPrice(5);
        dto.setMaxQuantity(100);
        dto.setMinQuantity(100);
        dto.setProduct(product);
        //dto.setLocked(false);
        dto.setStartDate(null);
        dto.setStartDeliveryDate(null);
        dto.setTitle("the best Gin Auction");
        dto.setUser(user);

        AuctionDto dto2 = new AuctionDto();
        dto2.setId(2);
        dto2.setDescription("Gin Auction");
        dto2.setEndDate(null);
        dto2.setEndDeliveryDate(null);
        dto2.setMaxPrice(10);
        dto2.setMinPrice(5);
        dto2.setMaxQuantity(100);
        dto2.setMinQuantity(100);
        dto2.setProduct(product);
        //dto2.setLocked(false);
        dto2.setStartDate(null);
        dto2.setStartDeliveryDate(null);
        dto2.setTitle("the best Gin Auction");
        dto2.setUser(user);

        List<AuctionDto> auctionDtos = new ArrayList<>();
        auctionDtos.add(dto);
        auctionDtos.add(dto2);

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(auctionDtos);



        Mockito.when(service.getNotClosedAuctions()).thenReturn(auctionDtos);

        mockMvc.perform(get("/api/auctions/notClosed"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(json));


    }

    @Test
    void updateAuction() throws Exception {
        ProductDto product = new ProductDto();
        product.setId(1);
        product.setTitle("The best Gin");
        product.setDescription("Very good gin for a good party!");
        product.setImages(null);

        UserDto user = new UserDto();
        user.setId(11);
        user.setFirstname("Test");
        user.setLastname("Test");
        user.setUsername("JonnyDoe123");
        user.setEmail("jonnydoe@test.at");
        user.setPassword("JonnyDoe123");
        user.setState(UserState.active);


        AuctionDto dto = new AuctionDto();
        dto.setDescription("Gin Auction");
        dto.setEndDate(null);
        dto.setEndDeliveryDate(null);
        dto.setMaxPrice(10);
        dto.setMinPrice(5);
        dto.setMaxQuantity(100);
        dto.setMinQuantity(100);
        dto.setProduct(product);
        //dto.setLocked(false);
        dto.setStartDate(null);
        dto.setStartDeliveryDate(null);
        dto.setTitle("the best Gin Auction");
        dto.setUser(user);

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(dto);

        ResponseEntity entity = new ResponseEntity(null, HttpStatus.OK);

        //Mockito.when(service.updateAuction(any(AuctionDto.class))).thenReturn(entity);

        mockMvc.perform(put("/api/auctions").contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(""));

    }

    @Test
    void updateAuctionWithoutDto() throws Exception {


        ResponseEntity entity = new ResponseEntity(null, HttpStatus.OK);

        mockMvc.perform(put("/api/auctions").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

    }

    @Test
    void toggleState() throws Exception {

        ResponseEntity entity = new ResponseEntity(AuctionState.locked, HttpStatus.OK);

        Mockito.when(service.toggleState(1)).thenReturn(AuctionState.locked);

        mockMvc.perform(put("/api/auctions/1/toggleState"))
                .andExpect(status().isOk());

    }
}