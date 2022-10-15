package at.barniverse.backend.barniverse_backend.model;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;

    private String description;

//    @OneToMany(mappedBy="product")
//    private Set<ProductImage> productImages;

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

//    public Set<ProductImage> getProductImages() {
//        return productImages;
//    }
//
//    public void setProductImages(Set<ProductImage> productImages) {
//        this.productImages = productImages;
//    }
}
