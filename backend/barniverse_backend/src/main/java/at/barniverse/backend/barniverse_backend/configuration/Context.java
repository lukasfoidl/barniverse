package at.barniverse.backend.barniverse_backend.configuration;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * context with basic error messages to be used application wide
 */
public class Context {

    /**
     * standardised error message for unspecific errors
     */
    public static final String ERROR = "Something went wrong, please try again later!";

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

    /**
     * standardised error message if access to a resource is denied
     */
    public static final String ACCESS_DENIED = "Access denied!";

    /**
     * standardised error message if a user is not authorized to access a resource
     */
    public static final String UNAUTHORIZED = "User is not authorized!";

    /**
     * standardised error message if the JWT Token is invalid
     */
    public static final String JWT_TOKEN_INVALID = "Invalid JWT Token!";

    /**
     * standardised error message if the JWT Token is expired
     */
    public static final String JWT_TOKEN_EXPIRED = "JWT Token is expired!";

    /**
     * standardised error message if the login credentials are invalid
     */
    public static final String INVALID_LOGIN_CREDENTIALS = "Invalid login credentials!";

    public static final String INVALID_STATE = "This account is no longer active. It has been blocked or deleted. Please contact the Barniverse service team for further information.";

    /**
     * application wide origins that are allowed for CORS requests
     */
    public static final String CORS_ORIGINS = "http://localhost:8080/";

}
