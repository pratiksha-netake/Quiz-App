package com.QuizApp.QuizApp.Controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> login(@RequestBody User user) {

        try{
        	Authentication authentication = authManager.authenticate(
        
                new UsernamePasswordAuthenticationToken(
                        user.getUsername(),
                        user.getPassword()
                )
        );

       

            User dbUser = repo.findByUsername(user.getUsername())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            String token= jwtUtil.generateToken(
                    dbUser.getUsername(),
                    dbUser.getRole()
            );
            
            Map <String,String> response=new HashMap<>();
            response.put("token", token);
            response.put("role", dbUser.getRole());
            
            return  ResponseEntity.ok(response);
        }catch(Exception e) {
        	Map<String ,String> error = new HashMap<>();
        	error.put("message", "Invalid username or password");
        	
        	return ResponseEntity
        			.status(401)
        			.body(error);
        }

       
    }
}