package com.ust.Security.service;


import com.ust.Security.model.Userinfo;
import com.ust.Security.repository.Userinforepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class Userservices {
    @Autowired
    private Userinforepository repo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    public String addUser(Userinfo userInfo) {
        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        repo.save(userInfo);
        return "user added to system ";
    }
}
