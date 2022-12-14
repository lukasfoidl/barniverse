package at.barniverse.backend.barniverse_backend.model;

import at.barniverse.backend.barniverse_backend.dto.IDto;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * entity for a product,
 * property definitions as well as getter and setter functions
 */
@Entity
public class Product implements IEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private int id;

    @NotBlank(message = "Name is mandatory!")
    private String name;

    @Size(max = 500, message = "Description must be shorter than 500 characters!")
    private String description;

    @OneToMany(targetEntity = ProductImage.class, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "product_Id", referencedColumnName = "id", foreignKey=@ForeignKey(name = "FK_productImage_productId"))
    private List<ProductImage> images;

//----getter and setter----

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
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
