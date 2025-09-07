package com.example.spring_boot_rest.controller;

import com.example.spring_boot_rest.model.User;
import com.example.spring_boot_rest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    public UserService userService;

    @PostMapping("/register")
    public void register(@RequestBody User user){
        userService.saveUser(user);
    }
}
