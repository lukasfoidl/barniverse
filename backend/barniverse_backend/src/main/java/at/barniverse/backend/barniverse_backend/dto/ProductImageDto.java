package at.barniverse.backend.barniverse_backend.dto;


/**
 * dto for product image entity,
 * property definitions as well as getter and setter functions
 */
public class ProductImageDto implements IDto {

    private int id;
    private String file;

//----getter and setter----

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

}
