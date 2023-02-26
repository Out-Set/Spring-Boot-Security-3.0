package com.example.demosecurity.Controller;

import com.example.demosecurity.Entity.User;
import com.example.demosecurity.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class Controller {

    @Autowired
    UserService service;

    @GetMapping("/welcome")
    public String welcome(){
        return "<h1>Welcome user</h1>";
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")   // // Assigning  admin-role
    public List<User> users(){
        return service.users();
    }

    @GetMapping("/hi")
    @PreAuthorize("hasAuthority('ROLE_USER')")  // Assigning  user-role
    public String hi() {
        return "hi user";
    }


    @PostMapping("/new")
    public String assNewUser(@RequestBody User userInfo) {
        return service.addUser(userInfo);
    }
}
