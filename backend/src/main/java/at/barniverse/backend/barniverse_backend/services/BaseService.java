package at.barniverse.backend.barniverse_backend.services;

import at.barniverse.backend.barniverse_backend.dto.IDto;
import at.barniverse.backend.barniverse_backend.model.IEntity;
import at.barniverse.backend.barniverse_backend.model.User;
import at.barniverse.backend.barniverse_backend.transformer.ITransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// base class for services with base functionality
@Service
public class BaseService {

    @Autowired
    private ValidationService validationService;

    // basic messages
    protected final String DATABASE_ERROR = "The transaction has been refused!";
    protected final String INVALID_ID = DATABASE_ERROR + " Invalid Id!";

    // create entity
    protected <T, U> ResponseEntity<Object> addEntity(CrudRepository<T, Integer> repository, ITransformer<T, U> transformer, U dto) {
        T entity= transformer.convertToEntity(dto);
        return validateAndSafeEntity(repository, entity);
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
    public <T, U> ResponseEntity<Object> updateEntity(CrudRepository<T, Integer> repository, ITransformer<T, U> transformer, IDto dto) {
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
        if (updatedEntity.getClass() == User.class) { // password should and can not be updated with standard user put request
            ((User) updatedEntity).setPassword(((User)dbEntity.get()).getPassword());
        }
        return validateAndSafeEntity(repository, updatedEntity);
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
    private <T> ResponseEntity<Object> validateAndSafeEntity(CrudRepository<T, Integer> repository, T entity) {
        ResponseEntity<Object> response = validationService.validateEntity(entity);
        return saveIfValid(repository, response, entity);
    }

    // extension method which validates a parent entity and all sub entities and saves the parent entity if valid
    protected <T> ResponseEntity<Object> validateAndSafeEntities(CrudRepository<T, Integer> repository, List<IEntity> allEntities, T parentEntity) {
        ResponseEntity<Object> response = validationService.validateEntities(allEntities);
        return saveIfValid(repository, response, parentEntity);
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
