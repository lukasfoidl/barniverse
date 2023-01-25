package at.barniverse.backend.barniverse_backend.services;

import at.barniverse.backend.barniverse_backend.exception.BarniverseException;
import at.barniverse.backend.barniverse_backend.transformer.ITransformer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
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
     * @throws BarniverseException in case of failure which includes error messages
     */
    protected <T, U> void addEntity(CrudRepository<T, Integer> repository, ITransformer<T, U> transformer, ValidationService<T> validationService, U dto) throws BarniverseException {
        T entity = transformer.convertToEntity(dto);
        validationService.validateEntity(entity);
        save(repository, entity);
    }

    /**
     * get all saved entities of one type from the database as dtos
     * @param repository type related repository
     * @param transformer type related transformer
     * @param <T> entity type
     * @param <U> dto type
     * @return loaded dtos
     * @throws BarniverseException in case of failure which includes error messages
     */
    protected <T, U> List<U> getEntitiesAsDtos(CrudRepository<T, Integer> repository, ITransformer<T, U> transformer) throws BarniverseException {
        Iterable<T> entities;
        try {
            entities = repository.findAll();
        } catch (Exception exception) {
            throw new BarniverseException(List.of(DATABASE_ERROR), HttpStatus.INTERNAL_SERVER_ERROR, exception);
        }
        List<U> dtos = new ArrayList<>();
        entities.forEach(entity -> {
            dtos.add(transformer.convertToDto(entity));
        });
        return dtos;
    }

    /**
     * get specific entity from the database as dto
     * @param repository type related repository
     * @param transformer type related transformer
     * @param id id of the specific entity
     * @param <T> entity type
     * @param <U> dto type
     * @return loaded dto
     * @throws BarniverseException in case of failure which includes error messages
     */
    protected <T, U> U getEntityAsDto(CrudRepository<T, Integer> repository, ITransformer<T, U> transformer, int id) throws BarniverseException {
        T entity = getEntity(repository, id);
        return transformer.convertToDto(entity);

            /*
            When testing it appeared that the dto reference was only send but not the values

            Object Mapper to Map Object to Json Type
            ObjectMapper objectMapper = new ObjectMapper();
            String json ="";
            try{
                 json = objectMapper.writeValueAsString(dto);
            }catch (Exception e){
                System.out.println(e);
            }
            when dto is send  the UserDto Reference will be send will be returned in the body */
    }

    /**
     * get specific entity from the database as entity
     * @param repository type related repository
     * @param id id of the specific entity
     * @param <T> entity type
     * @return loaded entity
     * @throws BarniverseException in case of failure which includes error messages
     */
    protected <T> T getEntity(CrudRepository<T, Integer> repository, int id) throws BarniverseException {
        Optional<T> entity;
        try {
            entity = repository.findById(id);
        } catch (Exception exception) {
            throw new BarniverseException(List.of(DATABASE_ERROR), HttpStatus.INTERNAL_SERVER_ERROR, exception);
        }
        if (entity.isEmpty()) {
            throw new BarniverseException(List.of(INVALID_ID), HttpStatus.BAD_REQUEST, null);
        }
        return entity.get();
    }

    /**
     * update specific entity in the database
     * @param repository type related repository
     * @param transformer type related transformer
     * @param validationService type related validationService
     * @param dto dto which should be updated
     * @param id id of the entity which should be updated
     * @param <T> entity type
     * @param <U> dto type
     * @throws BarniverseException in case of failure which includes error messages
     */
    protected <T, U> void updateEntity(CrudRepository<T, Integer> repository, ITransformer<T, U> transformer, ValidationService<T> validationService, U dto, int id) throws BarniverseException {
        T dbEntity = getEntity(repository, id);
        T updatedEntity = transformer.convertToEntity(dto);
        updatedEntity = transformer.repairEntity(updatedEntity, dbEntity);
        validationService.validateEntity(updatedEntity);
        save(repository, updatedEntity);
    }

    /**
     * delete specific entity from the database
     * @param repository type related repository
     * @param id id of the specific entity
     * @param <T> entity type
     * @throws BarniverseException in case of failure which includes error messages
     */
    protected <T> void deleteEntity(CrudRepository<T, Integer> repository, int id) throws BarniverseException {
        getEntity(repository, id);
        try {
            repository.deleteById(id);
        } catch (Exception exception) {
            throw new BarniverseException(List.of(DATABASE_ERROR), HttpStatus.INTERNAL_SERVER_ERROR, exception);
        }
    }

    /**
     * extension method which saves an entity in the database
     * @param repository type related repository
     * @param entity entity which should be saved
     * @param <T> entity type
     * @throws BarniverseException in case of failure which includes error messages
     */
    protected <T> void save(CrudRepository<T, Integer> repository, T entity) throws BarniverseException {
        try {
            repository.save(entity);
        } catch (Exception exception) {
            throw new BarniverseException(List.of(DATABASE_ERROR), HttpStatus.INTERNAL_SERVER_ERROR, exception);
        }
    }

    /**
     * extension method which saves an entity in the database and returns the corresponding dto <br>
     * WARNING: Use method only if values of the saved entity are explicitly needed, otherwise use method 'save'!
     * @param repository type related repository
     * @param transformer type related transformer
     * @param entity entity which should be saved
     * @param <T> entity type
     * @param <U> dto type
     * @return saved dto
     * @throws BarniverseException in case of failure which includes error messages
     */
    protected <T, U> U saveAndGetDto(CrudRepository<T, Integer> repository, ITransformer<T, U> transformer, T entity) throws BarniverseException {
        try {
            T dbEntity = repository.save(entity);
            return transformer.convertToDto(dbEntity);
        } catch (Exception exception) {
            throw new BarniverseException(List.of(DATABASE_ERROR), HttpStatus.INTERNAL_SERVER_ERROR, exception);
        }
    }

    /**
     * extension method which converts entities of one type to the corresponding dtos
     * @param transformer type related tranformer
     * @param entities entities which should be transformed
     * @param <T> entity type
     * @param <U> dto type
     * @return dtos
     */
    protected <T, U> List<U> convertToDto(ITransformer<T, U> transformer, List<T> entities) {
        List<U> dtos = new ArrayList<>(Collections.emptyList());
        for (T entity : entities) {
            dtos.add(transformer.convertToDto(entity));
        }
        return dtos;
    }
}
