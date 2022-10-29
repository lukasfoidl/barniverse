package at.barniverse.backend.barniverse_backend.model;

import at.barniverse.backend.barniverse_backend.enums.OStatus;
import at.barniverse.backend.barniverse_backend.validation.AfterToday;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.util.Date;

// offer model
@Entity
public class Offer implements IEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull()
    private int id;

    @NotNull(message = "Price is mandatory!")
    @DecimalMin(value = "0.0", message = "Price must be greater than 0.0!")
    private double price;

    @NotNull(message = "Quantity is mandatory!")
    @DecimalMin(value = "0.0", message = "Quantity must be greater than 0.0!")
    private double quantity;

    @NotNull(message = "Delivery date is mandatory!")
    @AfterToday(message = "Delivery date needs to be in the future!")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Date deliveryDate;

    @NotNull(message = "Status of order is mandatory!")
    private OStatus status;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "user_Id", referencedColumnName = "id", foreignKey=@ForeignKey(name = "FK_offer_userId"))
    @NotNull
    private User user;

    @ManyToOne(targetEntity = Auction.class)
    @JoinColumn(name = "auction_Id", referencedColumnName = "id", foreignKey=@ForeignKey(name = "FK_offer_auctionId"))
    @NotNull
    private Auction auction;

//----getter and setter----

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public OStatus getStatus() {
        return status;
    }

    public void setStatus(OStatus status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Auction getAuction() {
        return auction;
    }

    public void setAuction(Auction auction) {
        this.auction = auction;
    }
}
