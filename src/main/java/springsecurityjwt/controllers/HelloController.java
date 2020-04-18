package springsecurityjwt.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
public class HelloController {

    @GetMapping("/public/hello")
    public String helloAll() {
        return "Hello World";
    }

    @PreAuthorize("hasAnyRole('USER')")
    @GetMapping("/private/user")
    public String helloSecuredUser() {
        return "Hello secured";
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/private/admin")
    public String helloAdmin() {
        return "Hello Admin";
    }


}
