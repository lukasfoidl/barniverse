package at.barniverse.backend.barniverse_backend.services;

import at.barniverse.backend.barniverse_backend.exception.BarniverseException;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ImageServiceTest {
    @InjectMocks
    ImageService imageService;

    String filename = "BerlinerLuft01.jpg";



    @Test
    void getImage() throws BarniverseException, IOException {
        InputStream in = getClass().getResourceAsStream("/images/" + filename);
        byte[] content = IOUtils.toByteArray(in);

        byte[] array = imageService.getImage(filename);

        assertEquals(array.length, content.length);
        assertArrayEquals(content, array);

    }
}