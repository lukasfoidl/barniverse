package at.barniverse.backend.barniverse_backend.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;
    private String description;

    @OneToMany(targetEntity = ProductImage.class, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "product_Id", referencedColumnName = "id", foreignKey=@ForeignKey(name = "FK_productImage_productId"))
    private List<ProductImage> images;

    // getter and setter


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ProductImage> getImages() {
        return images;
    }

    public void setImages(List<ProductImage> images) {
        this.images = images;
    }
}
