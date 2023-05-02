package com.herman87.springsecurityH87.service;

import com.herman87.springsecurityH87.entity.User;
import com.herman87.springsecurityH87.entity.VerificationToken;
import com.herman87.springsecurityH87.model.UserModel;
import com.herman87.springsecurityH87.repository.UserRepository;
import com.herman87.springsecurityH87.repository.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private VerificationTokenRepository verificationTokenRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public User register(UserModel userModel) {
        User user = new User();
        user.setEmail(userModel.getEmail());
        user.setFirstName(userModel.getFirstName());
        user.setLastName(userModel.getLastName());
        user.setRole("USER");
        user.setPassword(passwordEncoder.encode(userModel.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public void saveVerificationTokenForUser(String token, User user) {
        verificationTokenRepository.save(new VerificationToken(user, token));
    }
}
