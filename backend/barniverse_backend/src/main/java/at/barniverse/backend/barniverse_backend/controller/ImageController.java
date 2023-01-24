package at.barniverse.backend.barniverse_backend.controller;

import at.barniverse.backend.barniverse_backend.exception.BarniverseException;
import at.barniverse.backend.barniverse_backend.services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static at.barniverse.backend.barniverse_backend.configuration.Context.CORS_ORIGINS;

/**
 * controller which handles image related requests
 */
@RestController
@CrossOrigin(origins = CORS_ORIGINS)
@RequestMapping(path= "/api")
public class ImageController {

    @Autowired private ImageService imageService;

    /**
     * get specific image from server
     * @param filename filename of the image
     * @return image
     */
    @GetMapping(value = "/images/{filename}")
    public ResponseEntity<Object> getImage(@PathVariable("filename") String filename) throws BarniverseException {
        byte[] content = imageService.getImage(filename);
        return new ResponseEntity<>(content, HttpStatus.OK);
    }

}