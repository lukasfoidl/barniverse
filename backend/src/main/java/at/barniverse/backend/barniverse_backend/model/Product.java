package at.barniverse.backend.barniverse_backend.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

// product model
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull
    private int id;

    @NotBlank(message = "Name is mandatory!")
    private String name;

    @Size(max = 500, message = "Description must be shorter than 500 characters!")
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
