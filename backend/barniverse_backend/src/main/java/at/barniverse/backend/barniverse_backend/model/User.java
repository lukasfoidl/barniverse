package at.barniverse.backend.barniverse_backend.model;

import at.barniverse.backend.barniverse_backend.enums.UserState;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * entity for a user,
 * property definitions, getter and setter functions as well as extension methods
 */
@Entity
public class User implements IEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private int id;

    @NotBlank(message = "Firstname is mandatory!")
    @Size(max = 50, message = "Firstname must be shorter than 50 characters!")
    private String firstname;

    @NotBlank(message = "Lastname is mandatory!")
    @Size(max = 50, message = "Lastname must be shorter than 50 characters!")
    private String lastname;

    @NotBlank(message = "Username is mandatory!")
    @Size(min = 5, max = 25, message = "Username has to be between 5 and 25 characters long!")
    private String username;

    @NotBlank(message = "Email is mandatory!")
    @Email(message = "Please provide a valid email address!")
    @Size(max = 50, message = "Email must be shorter than 50 characters!")
    private String email;

    @NotBlank(message = "Password is mandatory!")
    @Size(min = 8, message = "Password has to be at least 8 characters long!")
    private String password;

    private String picture;

    @NotNull(message = "Definition if user is admin or not is mandatory!")
    private boolean isAdmin;

    @NotNull(message = "State of user is mandatory!")
    private UserState state;

//----getter and setter----

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
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

    public boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean admin) {
        isAdmin = admin;
    }

    public UserState getState() {
        return state;
    }

    public void setState(UserState state) {
        this.state = state;
    }

//----extension methods----

    public boolean isActive() {
        return getState() == UserState.active;
    }

}
