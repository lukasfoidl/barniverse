package at.barniverse.backend.barniverse_backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.validation.*;
import java.util.*;

// handles validations
@Service
abstract public class ValidationService<T> {

    @Autowired
    private Validator validator;

    abstract public List<String> validateEntitySpecificExtras(T entity);

    // validate entity and return response
    public ResponseEntity<Object> validateEntity(T entity) {
        List<String> errors = validate(entity);
        if (!errors.isEmpty()) {
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    // validate entity and return plain errors
    public List<String> validateEntityGetErrors(T entity) {
        return validate(entity);
    }

    // extension method validate annotation of entity
    private List<String> validateAnnotations(T entity) {
        Set<ConstraintViolation<Object>> violations = validator.validate(entity);
        List<String> errors = new ArrayList<>();
        if (!violations.isEmpty()) {
            violations.forEach((error) -> {
                errors.add(error.getMessage());
            });
        }
        return errors;
    }

    // extension method validate entity
    private List<String> validate(T entity) {
        List<String> errors = validateAnnotations(entity);
        errors.addAll(validateEntitySpecificExtras(entity));
        return errors;
    }
}
