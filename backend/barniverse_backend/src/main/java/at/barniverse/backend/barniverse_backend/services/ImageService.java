package at.barniverse.backend.barniverse_backend.services;

import at.barniverse.backend.barniverse_backend.exception.BarniverseException;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

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
    public byte[] getImage(String filename) throws BarniverseException {
        byte[] content;
        try {
            InputStream in = getClass().getResourceAsStream("/images/" + filename);
            content = IOUtils.toByteArray(in);
        } catch (IOException exception) {
            throw new BarniverseException(List.of(ERROR), HttpStatus.INTERNAL_SERVER_ERROR, exception);
        }
        return content;
    }
}
