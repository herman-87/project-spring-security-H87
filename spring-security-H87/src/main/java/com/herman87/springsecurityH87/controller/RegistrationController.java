package com.herman87.springsecurityH87.controller;

import com.herman87.springsecurityH87.entity.User;
import com.herman87.springsecurityH87.entity.VerificationToken;
import com.herman87.springsecurityH87.event.RegistrationCompleteEvent;
import com.herman87.springsecurityH87.model.UserModel;
import com.herman87.springsecurityH87.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@Slf4j
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

    @GetMapping("/resendVerifyToken")
    public String resendVerificationToken(@RequestParam("token") String oldToken,
                                          HttpServletRequest request) {
        VerificationToken verificationToken = userService.generateNewVerificationToken(oldToken);
        User user = verificationToken.getUser();
        resendVerificationTokenMail(user, applicationUrl(request), verificationToken);
        return "verification link sent";

    }

    private void resendVerificationTokenMail(
            User user,
            String applicationUrl,
            VerificationToken verificationToken
    ) {
        String url =
                applicationUrl + "/verifyRegistration?token=" + verificationToken.getToken();

        //sendVerificationEmail
        log.info("Click the link to verify your account: {}", url);
    }

    @GetMapping("/verifyRegistration")
    public String verifyRegistration(@RequestParam("token") String token) {
        String result = userService.validateRegistrationToken(token);
        if (result.equalsIgnoreCase("valid")) {
            return "User Verifies Successfully";
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
