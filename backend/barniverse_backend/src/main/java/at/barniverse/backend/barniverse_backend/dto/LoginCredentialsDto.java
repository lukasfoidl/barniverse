package at.barniverse.backend.barniverse_backend.dto;

/**
 * dto for login credentials,
 * property definitions as well as getter and setter functions
 */
public class LoginCredentialsDto {

    private String email;
    private String password;

    public LoginCredentialsDto(String email, String password) {
        this.email = email;
        this.password = password;
    }

//----getter and setter----

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
}
