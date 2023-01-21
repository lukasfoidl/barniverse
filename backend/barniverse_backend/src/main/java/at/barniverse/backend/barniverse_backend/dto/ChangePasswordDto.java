package at.barniverse.backend.barniverse_backend.dto;

/**
 * dto for changing user password,
 * property definitions as well as getter and setter functions
 */
public class ChangePasswordDto {

    private int id;
    private String password;

    public ChangePasswordDto(int id, String password) {
        this.id = id;
        this.password = password;
    }

    public ChangePasswordDto() { }

//----getter and setter----

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
