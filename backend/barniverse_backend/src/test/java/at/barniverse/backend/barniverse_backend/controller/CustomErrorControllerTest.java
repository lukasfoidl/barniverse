package at.barniverse.backend.barniverse_backend.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CustomErrorControllerTest {

    CustomErrorController customErrorController;

    MockMvc mvc ;


    @BeforeEach
    void setup(){
        mvc = MockMvcBuilders.standaloneSetup(customErrorController).build();

    }



    @Test
    void handleError() throws Exception {


    }
}