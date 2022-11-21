package at.barniverse.backend.barniverse_backend.transformer;

/**
 * defines basic functions for transformers, all transformer classes need to implement this interface
 * @param <T> entity type
 * @param <U> dto type
 */
public interface ITransformer<T, U>
{
    /**
     * transforms entity to dto
     * @param entity entity which should be transformed
     * @return dto
     */
    U convertToDto(T entity);

    /**
     * transforms dto to entity
     * @param dto dto which should be transformed
     * @return entity
     */
    T convertToEntity(U dto);

    /**
     * repairs entity after transformation (setting properties to needed values)
     * @param entity entity which needs to be repaired
     * @param dbEntity entity with the missing data
     * @return repaired entity
     */
    T repairEntity(T entity, T dbEntity);
}
