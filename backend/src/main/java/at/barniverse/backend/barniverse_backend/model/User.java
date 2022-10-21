package at.barniverse.backend.barniverse_backend.model;

import at.barniverse.backend.barniverse_backend.enums.UStatus;

import javax.persistence.*;
import java.util.List;

// user model
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String firstname;
    private String lastname;
    private String username;
    private String email;
    private String password;
    private String picture;
    private boolean isAdmin;
    private UStatus status;

    @OneToMany(targetEntity = Auction.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_Id", referencedColumnName = "id", foreignKey=@ForeignKey(name = "FK_auction_userId"))
    private List<Auction> auctions;

    @OneToMany(targetEntity = Offer.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_Id", referencedColumnName = "id", foreignKey=@ForeignKey(name = "FK_offer_userId"))
    private List<Offer> offers;

    // getter and setter

    public int getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public UStatus getStatus() {
        return status;
    }

    public void setStatus(UStatus status) {
        this.status = status;
    }

    public List<Auction> getAuctions() {
        return auctions;
    }

    public void setAuctions(List<Auction> auctions) {
        this.auctions = auctions;
    }

    public List<Offer> getOffers() {
        return offers;
    }

    public void setOffers(List<Offer> offers) {
        this.offers = offers;
    }
}
