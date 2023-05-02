package com.herman87.springsecurityH87.listener;

import com.herman87.springsecurityH87.entity.User;
import com.herman87.springsecurityH87.event.RegistrationCompleteEvent;
import com.herman87.springsecurityH87.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
public class RegistrationCompleteEventListener implements ApplicationListener<RegistrationCompleteEvent> {
    @Autowired
    private UserService userService;

    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {
        // create the verification token for the user with link
        User user = event.getUser();
        String token = UUID.randomUUID().toString();
        userService.saveVerificationTokenForUser(token, user);

        //Send mail to user
        String url = event.getApplicationUrl() + "/verifyRegistration?token=" + token;

        //sendVerificationEmail()
        log.info("Click the link to verify your account: {}", url);
     }
}
