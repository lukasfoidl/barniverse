package at.barniverse.backend.barniverse_backend.dto;

import at.barniverse.backend.barniverse_backend.enums.ProductState;

import java.util.List;

/**
 * dto for offer entity,
 * property definitions as well as getter and setter functions
 */
public class ProductDto implements IDto {

    private int id;
    private String name;
    private String description;
    private ProductState state;
    private List<ProductImageDto> images;

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

    public ProductState getState() {
        return state;
    }

    public void setState(ProductState state) {
        this.state = state;
    }

    public List<ProductImageDto> getImages() {
        return images;
    }

    public void setImages(List<ProductImageDto> images) {
        this.images = images;
    }

}