package com.QuizApp.QuizApp.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.QuizApp.QuizApp.Dao.UserRepo;
import com.QuizApp.QuizApp.Model.User;
import com.QuizApp.QuizApp.Security.JwtUtil;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepo repo;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private AuthenticationManager authManager;

   
    @PostMapping("/register")
    public String register(@RequestBody User user) {

        if (repo.findByUsername(user.getUsername()).isPresent()) {
            return "Username already exists";
        }

        user.setPassword(encoder.encode(user.getPassword()));

        if (user.getRole() == null || user.getRole().isEmpty()) {
            user.setRole("ROLE_USER");
        }

        repo.save(user);

        return "User Registered Successfully";
    }

   
    @PostMapping("/login")
    public String login(@RequestBody User user) {

        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getUsername(),
                        user.getPassword()
                )
        );

        if (authentication.isAuthenticated()) {

            User dbUser = repo.findByUsername(user.getUsername())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            return jwtUtil.generateToken(
                    dbUser.getUsername(),
                    dbUser.getRole()
            );
        }

        return "Invalid Login";
    }
}