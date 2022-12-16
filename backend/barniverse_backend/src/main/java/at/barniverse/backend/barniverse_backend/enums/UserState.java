package at.barniverse.backend.barniverse_backend.enums;

/**
 * state for users
 */
public enum UserState {

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
     * In contrast to the state "blocked", deleted users cannot be set to "active" again. (2)
     */
    deleted

}
