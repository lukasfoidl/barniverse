package at.barniverse.backend.barniverse_backend.configuration;

/**
 * context with basic error messages to be used application wide
 */
public class Context {

    /**
     * standardised error message for database errors
     */
    public static final String DATABASE_ERROR = "The transaction has been refused!";

    /**
     * standardised error message for database errors caused by invalid id inputs
     */
    public static final String INVALID_ID = DATABASE_ERROR + " Invalid Id!";

    /**
     * standardised error message for validation errors of entity specific extras like foreign keys, subentities, etc.
     */
    public static final String VALIDATION_ERROR = DATABASE_ERROR + "Validation could not be finished!";

}
