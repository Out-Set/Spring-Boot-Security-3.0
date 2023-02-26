package com.example.demosecurity.Service;

import com.example.demosecurity.Entity.User;
import com.example.demosecurity.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> users(){
        return repository.findAll();
    }

    public User user(int id){
        return repository.findById(id);
    }

    public String addUser(User userInfo) {
        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        repository.save(userInfo);

        return "User added to system";
    }
}
