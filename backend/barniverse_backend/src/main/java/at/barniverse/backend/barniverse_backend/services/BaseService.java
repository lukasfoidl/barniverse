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

// base class for services with base functionality
@Service
abstract public class BaseService {

    // create entity
    protected <T, U> ResponseEntity<Object> addEntity(CrudRepository<T, Integer> repository, ITransformer<T, U> transformer, ValidationService<T> validationService, U dto) {
        T entity = transformer.convertToEntity(dto);
        return validateAndSafeEntity(repository, validationService, entity);
    }

    // read entities
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

    // read specific entity
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

    // update entity
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
        return validateAndSafeEntity(repository, validationService, updatedEntity);
    }

    // delete entity
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

    // extension method which validates a single entity and saves it if valid
    private <T> ResponseEntity<Object> validateAndSafeEntity(CrudRepository<T, Integer> repository, ValidationService<T> validationService, T entity) {
        ResponseEntity<Object> response = validationService.validateEntity(entity);
        return saveIfValid(repository, response, entity);
    }

    // validates a parent entity and all subentities and saves the parent entity if valid (subentities should get saved automatically with parent)
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

    // extension method which saves a entity if valid
    private <T> ResponseEntity<Object> saveIfValid(CrudRepository<T, Integer> repository, ResponseEntity<Object> response, T entity) {
        if (response.getStatusCode() == HttpStatus.OK) {
            try {
                repository.save(entity);
            } catch (Exception exception) {
                return new ResponseEntity<>(DATABASE_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return response;
    }

}