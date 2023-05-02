package com.herman87.springsecurityH87.controller;

import com.herman87.springsecurityH87.entity.User;
import com.herman87.springsecurityH87.event.RegistrationCompleteEvent;
import com.herman87.springsecurityH87.model.UserModel;
import com.herman87.springsecurityH87.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.http.HttpRequest;

@RestController
public class RegistrationController {
    @Autowired
    private UserService userService;
    @Autowired
    private ApplicationEventPublisher publisher;

    @PostMapping("/register")
    public String register(@RequestBody UserModel userModel, final HttpServletRequest request) {
        User user = userService.register(userModel);
        publisher.publishEvent(new RegistrationCompleteEvent(
                user,
                applicationUrl(request)
        ));
        return "Success";
    }

    @GetMapping("/verifyRegistration")
    public String verifyRegistration(@RequestParam("token") String token) {
        String result = userService.validateRegistrationToken(token);
        if (result.equalsIgnoreCase("valid")) {
            return "User Verifies Succcessfully";
        }
        return "Bad user";
    }

    private String applicationUrl(HttpServletRequest request) {
        return "http://" +
                request.getServerName() +
                ":" + request.getServerPort() +
                request.getContextPath();
    }
}
