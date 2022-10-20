package at.barniverse.backend.barniverse_backend.model;

import at.barniverse.backend.barniverse_backend.enums.OStatus;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Offer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private double price;
    private double quantity;
    private Date deliveryDate;
    private OStatus status;

    // getter and setter

    public int getId() {
        return id;
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
}
