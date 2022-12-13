package at.barniverse.backend.barniverse_backend.services;

import at.barniverse.backend.barniverse_backend.dto.IDto;
import at.barniverse.backend.barniverse_backend.model.IEntity;
import at.barniverse.backend.barniverse_backend.transformer.ITransformer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static at.barniverse.backend.barniverse_backend.configuration.Context.DATABASE_ERROR;
import static at.barniverse.backend.barniverse_backend.configuration.Context.INVALID_ID;

/**
 * base service with base functionality
 */
@Service
abstract public class BaseService {

    /**
     * add an entity to the database
     * @param repository type related repository
     * @param transformer type related transformer
     * @param validationService type related validationService
     * @param dto dto which should be saved
     * @param <T> entity type
     * @param <U> dto type
     * @return response with corresponding status code and added entity or error message in case of failure
     */
    protected <T, U> ResponseEntity<Object> addEntity(CrudRepository<T, Integer> repository, ITransformer<T, U> transformer, ValidationService<T> validationService, U dto) {
        T entity = transformer.convertToEntity(dto);
        return validateAndSaveEntity(repository, validationService, entity);
    }

    /**
     * get all saved entities of one type from the database
     * @param repository type related repository
     * @param transformer type related transformer
     * @param <T> entity type
     * @param <U> dto type
     * @return response with corresponding status code and loaded dtos or error message in case of failure
     */
    protected <T, U> ResponseEntity<Object> getEntities(CrudRepository<T, Integer> repository, ITransformer<T, U> transformer) {
        Iterable<T> entities;
        try {
            entities = repository.findAll();
        } catch (Exception exception) {
            return new ResponseEntity<>(DATABASE_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        List<U> dtos = new ArrayList<>();
        entities.forEach(entity -> {
            dtos.add(transformer.convertToDto(entity));
        });
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    /**
     * get specific entity from the database
     * @param repository type related repository
     * @param transformer type related transformer
     * @param id id of the specific entity
     * @param <T> entity type
     * @param <U> dto type
     * @return response with corresponding status code and loaded dto or error message in case of failure
     */
    public <T, U> ResponseEntity<Object> getEntity(CrudRepository<T, Integer> repository, ITransformer<T, U> transformer, int id) {
        Optional<T> entity;
        try {
            entity = repository.findById(id);
        } catch (Exception exception) {
            return new ResponseEntity<>(DATABASE_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (entity.isEmpty()) {
            return new ResponseEntity<>(INVALID_ID, HttpStatus.BAD_REQUEST);
        }
        U dto = transformer.convertToDto(entity.get());
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    /**
     * update specific entity in the database
     * @param repository type related repository
     * @param transformer type related transformer
     * @param validationService type related validationService
     * @param dto dto which should be updated
     * @param <T> entity type
     * @param <U> dto type
     * @return response with corresponding status code and updated entity or error message in case of failure
     */
    public <T, U> ResponseEntity<Object> updateEntity(CrudRepository<T, Integer> repository, ITransformer<T, U> transformer, ValidationService<T> validationService, IDto dto) {
        Optional<IEntity> dbEntity;
        try {
            dbEntity = (Optional<IEntity>) repository.findById(dto.getId());
        } catch (Exception exception) {
            return new ResponseEntity<>(DATABASE_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (dbEntity.isEmpty() || dbEntity.get().getId() != dto.getId()) {
            return new ResponseEntity<>(INVALID_ID, HttpStatus.BAD_REQUEST);
        }
        T updatedEntity = transformer.convertToEntity((U) dto);
        updatedEntity = transformer.repairEntity(updatedEntity, (T) dbEntity.get());
        return validateAndSaveEntity(repository, validationService, updatedEntity);
    }

    /**
     * delete specific entity from the database
     * @param repository type related repository
     * @param id id of the specific entity
     * @param <T> entity type
     * @return response with corresponding status code and error message in case of failure
     */
    protected <T> ResponseEntity<Object> deleteEntity(CrudRepository<T, Integer> repository, int id) {
        try {
            boolean exists = repository.existsById(id);
            if (!exists) {
                return new ResponseEntity<>(INVALID_ID, HttpStatus.BAD_REQUEST);
            }
            repository.deleteById(id);
        } catch (Exception exception) {
            return new ResponseEntity<>(DATABASE_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    /**
     * extension method which validates a single entity and saves it if valid
     * @param repository type related repository
     * @param validationService type related validationService
     * @param entity entity which should be saved
     * @param <T> entity type
     * @return response with corresponding status code and saved entity or error message in case of failure
     */
    private <T> ResponseEntity<Object> validateAndSaveEntity(CrudRepository<T, Integer> repository, ValidationService<T> validationService, T entity) {
        ResponseEntity<Object> response = validationService.validateEntity(entity);
        return saveIfValid(repository, response, entity);
    }

    /**
     * extension method which validates a parent entity and all subentities and saves the parent entity if valid, <br>
     * subentities should get saved automatically with parent
     * @param parentRepository parent type related repository
     * @param parentValidationService parent type related validationService
     * @param parentEntity parent entity which should be saved
     * @param childValidationService child type related validationService
     * @param childrenEntities children entities which should be validated
     * @param <T> parent entity type
     * @param <V> children entity type
     * @return response with corresponding status code and saved parent entity or error message in case of failure
     */
    protected <T, V> ResponseEntity<Object> validateWithSubentitiesAndSaveEntity(
            CrudRepository<T, Integer> parentRepository,
            ValidationService<T> parentValidationService,
            T parentEntity,
            ValidationService<V> childValidationService,
            List<V> childrenEntities) {
        List<String> errors = new ArrayList<>(parentValidationService.validateEntityGetErrors(parentEntity));
        childrenEntities.forEach(childEntity -> {
            errors.addAll(childValidationService.validateEntityGetErrors(childEntity));
        });
        if (!errors.isEmpty()) {
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }
        return saveIfValid(parentRepository, new ResponseEntity<>(null, HttpStatus.OK), parentEntity);
    }

    /**
     * extension method which saves an entity in the database if valid
     * @param repository type related repository
     * @param response response with corresponding status code and error message in case of failure of validation
     * @param entity entity which should be saved
     * @param <T> entity type
     * @return response with corresponding status code and saved entity or error message in case of failure
     */
    private <T> ResponseEntity<Object> saveIfValid(CrudRepository<T, Integer> repository, ResponseEntity<Object> response, T entity) {
        if (response.getStatusCode() == HttpStatus.OK) {
            try {
                T dbEntity = repository.save(entity);
                return new ResponseEntity<>(dbEntity, HttpStatus.OK);
            } catch (Exception exception) {
                return new ResponseEntity<>(DATABASE_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return response;
    }

}
