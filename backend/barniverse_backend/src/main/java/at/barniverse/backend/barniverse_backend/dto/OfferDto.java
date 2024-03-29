package at.barniverse.backend.barniverse_backend.dto;

import at.barniverse.backend.barniverse_backend.enums.OfferState;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * dto for offer entity,
 * property definitions as well as getter and setter functions
 */
public class OfferDto implements IDto {

    private int id;
    private double price;
    private double quantity;
    private LocalDateTime deliveryDate;
    private OfferState state;
    private UserDto user;
    private AuctionDto auction;

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

    public LocalDateTime getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDateTime deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public OfferState getState() {
        return state;
    }

    public void setState(OfferState state) {
        this.state = state;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public AuctionDto getAuction() {
        return auction;
    }

    public void setAuction(AuctionDto auction) {
        this.auction = auction;
    }

}
