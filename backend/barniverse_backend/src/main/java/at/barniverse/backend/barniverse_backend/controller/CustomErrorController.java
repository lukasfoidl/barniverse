package at.barniverse.backend.barniverse_backend.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * controller which handles specific error messages thrown in the security configuration
 */
@RestController
public class CustomErrorController implements ErrorController {

    private static final String PATH = "/error";

    /**
     * convert HttpServletRequests in responses the client can work with
     * @param request error request with status code and message which needs to be converted
     * @param response not used
     * @return error message with corresponding status code
     */
    @RequestMapping(PATH)
    public ResponseEntity<Object> handleError(final HttpServletRequest request, final HttpServletResponse response) {
        String message = (String) request.getAttribute("javax.servlet.error.message");
        HttpStatus status = HttpStatus.resolve((Integer) request.getAttribute("javax.servlet.error.status_code"));
        assert status != null;
        return new ResponseEntity<>(message, status);
    }
}
