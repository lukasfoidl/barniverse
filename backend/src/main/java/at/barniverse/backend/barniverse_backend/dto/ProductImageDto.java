package at.barniverse.backend.barniverse_backend.dto;


// product image dto
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
