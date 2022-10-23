package at.barniverse.backend.barniverse_backend.model;

import at.barniverse.backend.barniverse_backend.validation.AfterSpecificDate;
import at.barniverse.backend.barniverse_backend.validation.LowerThanOther;
import at.barniverse.backend.barniverse_backend.validation.AfterToday;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Date;
import java.util.List;

// auction model
@Entity
@LowerThanOther(lowerField = "minPrice", higherField = "maxPrice", message = "Max price must be higher than min price!")
@LowerThanOther(lowerField = "minQuantity", higherField = "maxQuantity", message = "Max quantity must be higher than min quantity!")
@AfterSpecificDate(startDate = "startDeliveryDate", endDate = "endDeliveryDate", message = "End delivery date needs to be after start delivery date!")
@AfterSpecificDate(startDate = "startDate", endDate = "endDate", message = "End date needs to be after start date!")
public class Auction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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
    @AfterToday(message = "Start delivery date needs to be in the future!")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Date startDeliveryDate;

    @NotNull(message = "End delivery date is mandatory!")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Date endDeliveryDate;

    @NotNull(message = "Start date is mandatory!")
    @AfterToday(message = "Start date needs to be in the future!")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Date startDate;

    @NotNull(message = "End date is mandatory!")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Date endDate;

    @NotNull(message = "Definition if auction is locked or not is mandatory!")
    private boolean locked;

    @OneToOne(targetEntity = Product.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "product_Id", referencedColumnName = "id", foreignKey=@ForeignKey(name = "FK_auction_productId"))
    private Product product;

    @OneToMany(targetEntity = Offer.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "auction_Id", referencedColumnName = "id", foreignKey=@ForeignKey(name = "FK_offer_auctionId"))
    private List<Offer> offers;

    // getter and setter

    public int getId() {
        return id;
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

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public List<Offer> getOffers() {
        return offers;
    }

    public void setOffers(List<Offer> offers) {
        this.offers = offers;
    }
}
