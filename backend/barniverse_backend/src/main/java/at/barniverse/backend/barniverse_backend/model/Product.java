package at.barniverse.backend.barniverse_backend.model;

import at.barniverse.backend.barniverse_backend.enums.ProductState;
import at.barniverse.backend.barniverse_backend.enums.UserState;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * entity for a product,
 * property definitions, getter and setter functions as well as extension methods
 */
@Entity
public class Product implements IEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private int id;

    @NotBlank(message = "Title is mandatory!")
    @Size(max = 50, message = "Title must be shorter than 50 characters!")
    private String title;

    @Size(max = 500, message = "Description must be shorter than 500 characters!")
    private String description;

    @NotNull(message = "State of product is mandatory!")
    private ProductState state;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ProductState getState() {
        return state;
    }

    public void setState(ProductState state) {
        this.state = state;
    }

    public List<ProductImage> getImages() {
        return images;
    }

    public void setImages(List<ProductImage> images) {
        this.images = images;
    }

//----extension methods----

    public boolean isActive() {
        return getState() == ProductState.active;
    }
}
