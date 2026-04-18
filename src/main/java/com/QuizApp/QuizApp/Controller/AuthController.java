package com.QuizApp.QuizApp.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.QuizApp.QuizApp.Dao.UserRepo;
import com.QuizApp.QuizApp.Model.User;

@RestController
public class AuthController {

    @Autowired
    private UserRepo repo;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private AuthenticationManager authManager;

    // ✅ REGISTER
    @PostMapping("/register")
    public String register(@RequestBody User user) {

        user.setPassword(encoder.encode(user.getPassword()));
        repo.save(user);

        return "User Registered Successfully";
    }

    // ✅ LOGIN
    @PostMapping("/login")
    public String login(@RequestBody User user) {

        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getUsername(),
                        user.getPassword()
                )
        );

        if (authentication.isAuthenticated()) {
            return "Login Successful";
        } else {
            return "Login Failed";
        }
    }
}