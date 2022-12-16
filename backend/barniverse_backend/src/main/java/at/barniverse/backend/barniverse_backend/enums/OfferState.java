package at.barniverse.backend.barniverse_backend.enums;

/**
 * state for offers
 */
public enum OfferState {

    /**
     * offer is still running (0)
     */
    running,

    /**
     * offer has been chosen from the auction creator and has won the auction (1)
     */
    accepted,

    /**
     * another offer has been chosen from the auction creator and this offer has lost the auction (2)
     */
    rejected

}
