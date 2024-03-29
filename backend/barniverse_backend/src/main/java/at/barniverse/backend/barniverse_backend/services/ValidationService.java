package at.barniverse.backend.barniverse_backend.services;

import at.barniverse.backend.barniverse_backend.exception.BarniverseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
     * @throws BarniverseException in case of failure which includes error messages
     */
    abstract public List<String> validateEntitySpecificExtras(T entity) throws BarniverseException;

    /**
     * validate entity
     * @param entity entity which should be validated
     * @throws BarniverseException in case of failure which includes error messages
     */
    public void validateEntity(T entity) throws BarniverseException {
        List<String> errors = validate(entity);
        if (!errors.isEmpty()) {
            throw new BarniverseException(errors, HttpStatus.BAD_REQUEST, null);
        }
    }

    /**
     * validate entity
     * @param entity entity which should be validated
     * @return error messages, empty if validation was successful
     * @throws BarniverseException in case of failure which includes error messages
     */
    public List<String> validateEntityGetErrors(T entity) throws BarniverseException {
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
     * @throws BarniverseException in case of failure which includes error messages
     */
    private List<String> validate(T entity) throws BarniverseException {
        List<String> errors = validateAnnotations(entity);
        errors.addAll(validateEntitySpecificExtras(entity));
        return errors;
    }
}
