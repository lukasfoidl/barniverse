package at.barniverse.backend.barniverse_backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Deprecated
@RestController
public class HelloWorldController {

    @GetMapping("")
    public String helloWorld() {
        return "Hello World!";
    }
}
