package at.barniverse.backend.barniverse_backend.enums;

/**
 * authorization roles
 */
public enum Role {

    /**
     * visitor of website, no authentication (0)
     */
    ROLE_VISITOR,

    /**
     * registered and logged in user (1)
     */
    ROLE_USER,

    /**
     * admin user (2)
     */
    ROLE_ADMIN
}
