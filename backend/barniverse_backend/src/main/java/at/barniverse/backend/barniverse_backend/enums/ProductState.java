package at.barniverse.backend.barniverse_backend.enums;

/**
 * state for products
 */
public enum ProductState {

    /**
     * product is active (0)
     */
    active,

    /**
     * product is deleted, record remains in the database to avoid foreign key conflicts and keeping history of auctions, offers, etc. (1)
     */
    deleted

}
