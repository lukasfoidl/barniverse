package at.barniverse.backend.barniverse_backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.validation.*;
import java.util.*;

/**
 * base validation service which handles validations
 * @param <T> entity type
 */
@Service
abstract public class ValidationService<T> {

    @Autowired
    private Validator validator;

    /**
     * extension method which validates entity specific extras (foreign keys, subentities, etc.)
     * @param entity entity which should be validated
     * @return error messages, empty if validation was successful
     */
    abstract public List<String> validateEntitySpecificExtras(T entity);

    /**
     * validates entity
     * @param entity entity which should be validated
     * @return response with corresponding status code and error message in case of failure
     */
    public ResponseEntity<Object> validateEntity(T entity) {
        List<String> errors = validate(entity);
        if (!errors.isEmpty()) {
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    /**
     * validates entity
     * @param entity entity which should be validated
     * @return error messages, empty if validation was successful
     */
    public List<String> validateEntityGetErrors(T entity) {
        return validate(entity);
    }

    /**
     * extension method which validates the annotations of an entity
     * @param entity entity which should be validated
     * @return error messages, empty if validation was successful
     */
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

    /**
     * extension method which validates an entity
     * @param entity entity which should be validated
     * @return error messages, empty if validation was successful
     */
    private List<String> validate(T entity) {
        List<String> errors = validateAnnotations(entity);
        errors.addAll(validateEntitySpecificExtras(entity));
        return errors;
    }
}
