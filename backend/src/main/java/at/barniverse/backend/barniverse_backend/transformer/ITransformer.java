package at.barniverse.backend.barniverse_backend.transformer;

public interface ITransformer<T, U>
{
    U convertToDto(T entity);
    T convertToEntity(U dto);
}
