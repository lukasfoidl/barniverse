package at.barniverse.backend.barniverse_backend.dto;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * dto for auction entity,
 * property definitions as well as getter and setter functions
 */
public class AuctionDto implements IDto {

    private int id;
    private String title;
    private String description;
    private double minPrice;
    private double maxPrice;
    private double minQuantity;
    private double maxQuantity;
    private LocalDateTime startDeliveryDate;
    private LocalDateTime endDeliveryDate;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private boolean locked;
    private UserDto user;
    private ProductDto product;

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

    public double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(double minPrice) {
        this.minPrice = minPrice;
    }

    public double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(double maxPrice) {
        this.maxPrice = maxPrice;
    }

    public double getMinQuantity() {
        return minQuantity;
    }

    public void setMinQuantity(double minQuantity) {
        this.minQuantity = minQuantity;
    }

    public double getMaxQuantity() {
        return maxQuantity;
    }

    public void setMaxQuantity(double maxQuantity) {
        this.maxQuantity = maxQuantity;
    }

    public LocalDateTime getStartDeliveryDate() {
        return startDeliveryDate;
    }

    public void setStartDeliveryDate(LocalDateTime startDeliveryDate) {
        this.startDeliveryDate = startDeliveryDate;
    }

    public LocalDateTime getEndDeliveryDate() {
        return endDeliveryDate;
    }

    public void setEndDeliveryDate(LocalDateTime endDeliveryDate) {
        this.endDeliveryDate = endDeliveryDate;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public ProductDto getProduct() {
        return product;
    }

    public void setProduct(ProductDto product) {
        this.product = product;
    }

}
