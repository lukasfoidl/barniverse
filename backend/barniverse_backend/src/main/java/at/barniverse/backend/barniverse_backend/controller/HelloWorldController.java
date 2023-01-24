package at.barniverse.backend.barniverse_backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * demo controller
 */
@Deprecated
@RestController
public class HelloWorldController {

    /**
     * demo route
     * @return string 'Hello World!'
     */
    @GetMapping("")
    public String helloWorld() {
        return "Hello World!";
    }
}
