package com.herman87.springsecurityH87.controller;

import com.herman87.springsecurityH87.entity.User;
import com.herman87.springsecurityH87.model.UserModel;
import com.herman87.springsecurityH87.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegistrationController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public String register(@RequestBody UserModel userModel) {
        User user = userService.register(userModel);
    }
}
