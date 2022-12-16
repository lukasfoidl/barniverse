package at.barniverse.backend.barniverse_backend.enums;

/**
 * authorization roles
 */
public enum Role {

    /**
     * visitor of website, no authentication
     */
    ROLE_VISITOR,

    /**
     * registered and logged in user
     */
    ROLE_USER,

    /**
     * admin user
     */
    ROLE_ADMIN
}
