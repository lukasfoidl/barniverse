package at.barniverse.backend.barniverse_backend.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * entity for a product image,
 * property definitions as well as getter and setter functions
 */
@Entity
public class ProductImage implements IEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private int id;

    @NotBlank(message = "Filepath is mandatory!")
    @Size(max = 500, message = "Filepath must be shorter than 500 characters!")
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
