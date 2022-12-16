package at.barniverse.backend.barniverse_backend.services;

import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;

import static at.barniverse.backend.barniverse_backend.configuration.Context.ERROR;

/**
 * service to handle image files on server
 */
@Service
public class ImageService {

    /**
     * get specific image from server
     * @param filename filename of the image
     * @return image
     */
    public ResponseEntity<Object> getImage(String filename) {
        byte[] content;
        try {
            InputStream in = getClass().getResourceAsStream("/images/" + filename);
            content = IOUtils.toByteArray(in);
        } catch (IOException exception) {
            return new ResponseEntity<>(ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return  new ResponseEntity<>(content, HttpStatus.OK);
    }
}
