package at.barniverse.backend.barniverse_backend.enums;

public class RoleConverter {

    public static boolean isAdmin(Role role) {
        return role == Role.ROLE_ADMIN;
    }

    public static Role getRole(boolean isAdmin) {
        if (isAdmin) {
            return Role.ROLE_ADMIN;
        }
        return Role.ROLE_USER;
    }
}
