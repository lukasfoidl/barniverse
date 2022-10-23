package at.barniverse.backend.barniverse_backend.services;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;

// handles responses to the client (correct status and success or error messages)
public class ResponseService {

    public static ResponseEntity<List<String>> createResponse(BindingResult bindingResult, String successMessage) {
        List<String> result = new ArrayList<>();
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach((error) -> {
                String errorMessage = error.getDefaultMessage();
                result.add(errorMessage);
            });
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        } else {
            result.add(successMessage);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }

    }
}
