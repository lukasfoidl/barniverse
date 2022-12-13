package at.barniverse.backend.barniverse_backend.dto;

public class AuthDto {

    private String email;
    private String username;
    private String role;
    private String uuid;

    public AuthDto(String email, String username, String role, String uuid) {
        this.email = email;
        this.username = username;
        this.role = role;
        this.uuid = uuid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
