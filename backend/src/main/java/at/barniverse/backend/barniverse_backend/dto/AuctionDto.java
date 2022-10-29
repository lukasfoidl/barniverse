package at.barniverse.backend.barniverse_backend.dto;

import java.util.Date;

// auction dto
public class AuctionDto implements IDto {

    private int id;
    private String title;
    private String description;
    private double minPrice;
    private double maxPrice;
    private double minQuantity;
    private double maxQuantity;
    private Date startDeliveryDate;
    private Date endDeliveryDate;
    private Date startDate;
    private Date endDate;
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

    public Date getStartDeliveryDate() {
        return startDeliveryDate;
    }

    public void setStartDeliveryDate(Date startDeliveryDate) {
        this.startDeliveryDate = startDeliveryDate;
    }

    public Date getEndDeliveryDate() {
        return endDeliveryDate;
    }

    public void setEndDeliveryDate(Date endDeliveryDate) {
        this.endDeliveryDate = endDeliveryDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
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
