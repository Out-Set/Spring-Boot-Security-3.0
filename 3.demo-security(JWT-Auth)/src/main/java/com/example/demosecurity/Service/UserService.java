package com.example.demosecurity.Service;

import com.example.demosecurity.Entity.UserInfo;
import com.example.demosecurity.Repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService {

    @Autowired
    UserInfoRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<UserInfo> users(){
        return repository.findAll();
    }

    public UserInfo user(int id){
        return repository.findById(id);
    }

    public String addUser(UserInfo userInfo) {
        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        repository.save(userInfo);

        return "User added to system";
    }
}
