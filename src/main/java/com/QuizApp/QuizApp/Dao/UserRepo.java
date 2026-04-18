package com.QuizApp.QuizApp.Dao;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.QuizApp.QuizApp.Model.User;

public interface UserRepo extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);
}