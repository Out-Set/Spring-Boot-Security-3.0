package com.example.demosecurity.Controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class Controller {

    @GetMapping("/welcome")
    public String welcome(){
        return "<h1>Welcome Here</h1>";
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")   // // Assigning  admin-role
    public String admin(){
        return "Hello from Admin";
    }

    @GetMapping("/hi")
    @PreAuthorize("hasAuthority('ROLE_USER')")  // Assigning  user-role
    public String user() {
        return "Hello form user";
    }
}
