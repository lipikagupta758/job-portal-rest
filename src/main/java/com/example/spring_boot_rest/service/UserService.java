package com.example.spring_boot_rest.service;

import com.example.spring_boot_rest.model.User;
import com.example.spring_boot_rest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository repo;
    private BCryptPasswordEncoder passwordEncoder= new BCryptPasswordEncoder(12);

    public void saveUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        repo.save(user);
    }
}
