package com.example.demosecurity.Controller;

import com.example.demosecurity.Dto.AuthRequest;
import com.example.demosecurity.Entity.UserInfo;
import com.example.demosecurity.Service.JwtService;
import com.example.demosecurity.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class Controller {

    @Autowired
    UserService service;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;
    // To Authenticate the Token

    @GetMapping("/welcome")
    public String welcome(){
        return "<h1>Welcome user</h1>";
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")   // // Assigning  admin-role
    public List<UserInfo> users(){
        return service.users();
    }

    @GetMapping("/hi")
    @PreAuthorize("hasAuthority('ROLE_USER')")  // Assigning  user-role
    public String hi() {
        return "hi user";
    }


    @PostMapping("/new")
    public String assNewUser(@RequestBody UserInfo userInfo) {
        return service.addUser(userInfo);
    }


    @PostMapping("/authenticate")
    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {

        // Getting the Authentication Object
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(authRequest.getUsername());
        } else {
            throw new UsernameNotFoundException("Invalid user request !!");
        }
    }
}
