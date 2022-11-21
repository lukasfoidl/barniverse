package at.barniverse.backend.barniverse_backend.enums;

/**
 * status for users
 */
public enum UStatus {

    /**
     * user is active (0)
     */
    active,

    /**
     * user is blocked, cannot login, create auctions, place offers etc. (1)
     */
    blocked,

    /**
     * user is deleted, record remains in the database to avoid foreign key conflicts and keeping history of auctions, offers, etc.
     * In contrast to the status "blocked", deleted users cannot be set to "active" again. (2)
     */
    deleted

}
