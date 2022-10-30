package at.barniverse.backend.barniverse_backend.model;

import at.barniverse.backend.barniverse_backend.dto.IDto;
import at.barniverse.backend.barniverse_backend.enums.UStatus;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

// user model
@Entity
public class User implements IEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private int id;

    @NotBlank(message = "Firstname is mandatory!")
    private String firstname;

    @NotBlank(message = "Lastname is mandatory!")
    private String lastname;

    @NotBlank(message = "Username is mandatory!")
    @Size(min = 5, message = "Username has to be at least 5 characters long!")
    private String username;

    @NotBlank(message = "Email is mandatory!")
    @Email(message = "Please provide a valid email address!")
    private String email;

    @NotBlank(message = "Password is mandatory!")
    @Size(min = 8, message = "Password has to be at least 8 characters long!")
    private String password;

    private String picture;

    @NotNull(message = "Definition if user is admin or not is mandatory!")
    private boolean isAdmin;

    @NotNull(message = "Status of user is mandatory!")
    private UStatus status;

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

}
