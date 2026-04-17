package com.QuizApp.QuizApp.Dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.QuizApp.QuizApp.Model.Quiz;

public interface QuizDao extends JpaRepository<Quiz,Integer> {

}
