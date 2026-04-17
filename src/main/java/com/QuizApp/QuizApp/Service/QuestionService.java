package com.QuizApp.QuizApp.Service;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.QuizApp.QuizApp.Dao.QuestionDao;
import com.QuizApp.QuizApp.Model.Question;


@Service
public class QuestionService {

    @Autowired
    private QuestionDao questionDao;
    

    public ResponseEntity<List<Question>> getAllQuestions() {
    	try {
        return new ResponseEntity<> (questionDao.findAll(),HttpStatus.OK);
    }catch(Exception e) {
    	e.printStackTrace();
    }
    	return new ResponseEntity<>(new ArrayList<>(),HttpStatus.BAD_REQUEST);
    }
    
    

	public ResponseEntity<List<Question>> getQuestionsByCategory(String category) {
		try {
		return new ResponseEntity<>(questionDao.findByCategoryIgnoreCase(category),HttpStatus.OK);
	}catch(Exception e) {
		e.printStackTrace();
		
	}
		return new ResponseEntity<>(new ArrayList<>(),HttpStatus.BAD_REQUEST);
	}
	
	

	public ResponseEntity<String> addQuestion(Question question) {
		 questionDao.save(question);
		 return new ResponseEntity<>("success",HttpStatus.CREATED);
		
	}
}
