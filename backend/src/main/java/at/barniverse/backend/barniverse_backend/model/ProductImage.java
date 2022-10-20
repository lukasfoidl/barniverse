package at.barniverse.backend.barniverse_backend.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class ProductImage {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String path;

    // getter and setter


    public int getId() {
        return id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
