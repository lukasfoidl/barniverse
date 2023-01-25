package at.barniverse.backend.barniverse_backend.exception;

import org.springframework.http.HttpStatus;

import java.util.List;

/**
 * exception thrown if task execution fails for some reason, property definitions as well as getter and setter functions
 */
public class BarniverseException extends Exception {

    private final HttpStatus status;
    private final List<String> errorMessages;

    public BarniverseException(List<String> errorMessages, HttpStatus status, Throwable err) {
        super("", err);
        this.errorMessages = errorMessages;
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public List<String> getErrorMessages() {
        return errorMessages;
    }
}
