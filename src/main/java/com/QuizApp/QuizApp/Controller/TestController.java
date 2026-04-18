package com.QuizApp.QuizApp.Controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
public class TestController {

    @GetMapping("/user/home")
    @PreAuthorize("hasRole('USER')")
    public String userHome() {
        return "User Access";
    }

    @GetMapping("/admin/home")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminHome() {
        return "Admin Access";
    }
}