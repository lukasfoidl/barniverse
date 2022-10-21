package at.barniverse.backend.barniverse_backend.model;

import javax.persistence.*;

// product image model
@Entity
public class ProductImage {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String file;

    // getter and setter

    public int getId() {
        return id;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }
}
