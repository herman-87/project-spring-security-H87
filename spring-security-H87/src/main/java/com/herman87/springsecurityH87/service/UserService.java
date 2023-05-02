package com.herman87.springsecurityH87.service;

import com.herman87.springsecurityH87.entity.User;
import com.herman87.springsecurityH87.model.UserModel;

public interface UserService {
    User register(UserModel userModel);

    void saveVerificationTokenForUser(String token, User user);

    String validateRegistrationToken(String token);
}
