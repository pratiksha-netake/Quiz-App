package com.QuizApp.QuizApp.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.QuizApp.QuizApp.Dao.UserRepo;
import com.QuizApp.QuizApp.Model.User;

@RestController
public class AuthController {

    @Autowired
    UserRepo repo;

    @Autowired
    PasswordEncoder encoder;

    @PostMapping("/register")
    public String register(@RequestParam String username,
                           @RequestParam String password,
                           @RequestParam String role) {

        User user = new User();
        user.setUsername(username);

        // 🔥 MUST ENCODE PASSWORD
        user.setPassword(encoder.encode(password));

        user.setRole(role);

        repo.save(user);

        return "User Registered Successfully";
    }
}