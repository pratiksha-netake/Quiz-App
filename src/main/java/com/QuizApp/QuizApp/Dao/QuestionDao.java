package com.QuizApp.QuizApp.Dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.QuizApp.QuizApp.Model.Question;

import jakarta.transaction.Transactional;


@Repository
public interface QuestionDao extends JpaRepository<Question, Integer> {
    List<Question> findByCategoryIgnoreCase(String  category);


    @Query(value = "SELECT * FROM question WHERE category = :category ORDER BY RAND() LIMIT :numQ", nativeQuery = true)
    List<Question> findRandomQuestionsByCategory(@Param("category") String category, @Param("numQ") int numQ);
    
    
    
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM quiz_questions WHERE questions_id = :id", nativeQuery = true)
    void deleteFromQuizQuestions(Integer id);

}