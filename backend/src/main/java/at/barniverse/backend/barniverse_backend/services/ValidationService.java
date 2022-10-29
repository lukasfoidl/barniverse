package at.barniverse.backend.barniverse_backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.validation.*;
import java.util.*;

// handles validations
@Service
public class ValidationService {

    @Autowired
    private Validator validator;

    public <T> ResponseEntity<Object> validateEntity(T entity) {
        List<String> errors = validate(entity);
        if (!errors.isEmpty()) {
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    public <T> ResponseEntity<Object> validateEntities(List<T> entities) {
        List<String> allErrors = new ArrayList<>();
        entities.forEach(entity -> {
            List<String> errors = validate(entity);
            if (!errors.isEmpty()) {
                allErrors.addAll(errors);
            }
        });
        if (!allErrors.isEmpty()) {
            return new ResponseEntity<>(allErrors, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    private <T> List<String> validate(T entity) {
        Set<ConstraintViolation<Object>> violations = validator.validate(entity);
        List<String> errors = new ArrayList<>();
        if (!violations.isEmpty()) {
            violations.forEach((error) -> {
                errors.add(error.getMessage());
            });
        }
        return errors;
    }
}
