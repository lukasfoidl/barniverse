package at.barniverse.backend.barniverse_backend.enums;

/**
 * converts enum role to isAdmin boolean value and back
 */
public class RoleConverter {

    /**
     * converts enum role to isAdmin boolean value
     * @param role role which should be converted
     * @return boolean value of role
     */
    public static boolean isAdmin(Role role) {
        return role == Role.ROLE_ADMIN;
    }

    /**
     * converts isAdmin boolean value to enum role
     * @param isAdmin boolean value which should be transformed
     * @return enum role of boolean value
     */
    public static Role getRole(boolean isAdmin) {
        if (isAdmin) {
            return Role.ROLE_ADMIN;
        }
        return Role.ROLE_USER;
    }
}
