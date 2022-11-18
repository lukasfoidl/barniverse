package at.barniverse.backend.barniverse_backend.model;

/**
 * defines basic functions for entities, all entity classes need to implement this interface
 */
public interface IEntity {

    /**
     * get the id property
     * @return id property
     */
    int getId();

    /**
     * set the id property
     * @param id new id
     */
    void setId(int id);

}
