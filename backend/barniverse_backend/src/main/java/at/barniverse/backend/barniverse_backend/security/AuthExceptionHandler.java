package at.barniverse.backend.barniverse_backend.security;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static at.barniverse.backend.barniverse_backend.configuration.Context.ACCESS_DENIED;

/**
 * extension class which handles authentication exceptions
 */
@ControllerAdvice
public class AuthExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * converts AuthenticationException into a response with status 403 and error message
     * @param ex exception thrown in the system
     * @return response with corresponding status code and error message
     */
    @ExceptionHandler({ AccessDeniedException.class })
    @ResponseBody
    public ResponseEntity<Object> handleAuthenticationException(Exception ex) {
        return new ResponseEntity<>(ACCESS_DENIED, HttpStatus.FORBIDDEN);
    }
}
