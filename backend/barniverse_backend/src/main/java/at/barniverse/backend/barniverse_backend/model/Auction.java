package at.barniverse.backend.barniverse_backend.model;

import at.barniverse.backend.barniverse_backend.enums.AuctionState;
import at.barniverse.backend.barniverse_backend.validation.AfterSpecificDate;
import at.barniverse.backend.barniverse_backend.validation.LowerOrEqualThanOther;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;

/**
 * entity for an auction,
 * property definitions, getter and setter functions as well as extension methods
 */
@Entity
@LowerOrEqualThanOther(lowerField = "minPrice", higherField = "maxPrice", message = "Max price must be higher than min price!")
@LowerOrEqualThanOther(lowerField = "minQuantity", higherField = "maxQuantity", message = "Max quantity must be higher than min quantity!")
@AfterSpecificDate(startDate = "startDate", endDate = "endDate", message = "End date needs to be after start date!")
@AfterSpecificDate(startDate = "endDate", endDate = "startDeliveryDate", message = "Start delivery date needs to be after end date!")
@AfterSpecificDate(startDate = "startDeliveryDate", endDate = "endDeliveryDate", message = "End delivery date needs to be after start delivery date!")
public class Auction implements IEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private int id;

    @NotBlank(message = "Title is mandatory!")
    private String title;

    @Size(max = 500, message = "Description must be shorter than 500 characters!")
    private String description;

    @NotNull(message = "Minimal price is mandatory!")
    @DecimalMin(value = "0.0", message = "Minimal price must be greater than 0.0!")
    private double minPrice;

    @NotNull(message = "Maximal price is mandatory!")
    @DecimalMin(value = "0.0", message = "Minimal price must be greater than 0.0!")
    private double maxPrice;

    @NotNull(message = "Minimal quantity is mandatory!")
    @DecimalMin(value = "0.0", message = "Minimal quantity must be greater than 0.0!")
    private double minQuantity;

    @NotNull(message = "Maximal quantity is mandatory!")
    @DecimalMin(value = "0.0", message = "Maximal quantity must be greater than 0.0!")
    private double maxQuantity;

    @NotNull(message = "Start delivery date is mandatory!")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime startDeliveryDate;

    @NotNull(message = "End delivery date is mandatory!")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime endDeliveryDate;

    @NotNull(message = "Start date is mandatory!")
    @Future(message = "Start date needs to be in the future!")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime startDate;

    @NotNull(message = "End date is mandatory!")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime endDate;

    @NotNull(message = "State of auction is mandatory!")
    private AuctionState state;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "user_Id", referencedColumnName = "id", foreignKey=@ForeignKey(name = "FK_auction_userId"))
    @NotNull
    private User user;

    @ManyToOne(targetEntity = Product.class)
    @JoinColumn(name = "product_Id", referencedColumnName = "id", foreignKey=@ForeignKey(name = "FK_auction_productId"))
    @NotNull
    private Product product;

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

    public AuctionState getState() {
        return state;
    }

    public void setState(AuctionState state) {
        this.state = state;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

//----extension methods----

    public boolean isActive() {
        return getState() == AuctionState.active;
    }

}
