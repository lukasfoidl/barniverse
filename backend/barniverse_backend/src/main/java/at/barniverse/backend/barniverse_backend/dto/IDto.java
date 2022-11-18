package at.barniverse.backend.barniverse_backend.dto;

/**
 * defines basic functions for dtos, all dto classes need to implement this interface
 */
public interface IDto {

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
