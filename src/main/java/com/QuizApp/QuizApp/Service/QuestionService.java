package com.QuizApp.QuizApp.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
		 
		return new ResponseEntity<>("Question Saved Successfully",HttpStatus.CREATED);
		
	}
	
	public ResponseEntity<Question> getQuestionById(Integer id) {

	    Optional<Question> question = questionDao.findById(id);

	    if (question.isPresent()) {
	        return new ResponseEntity<>(question.get(), HttpStatus.OK);
	    } else {
	        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
	}



	public ResponseEntity<String> updateQuestion(Integer id, Question updatedQuestion) {
		Optional<Question> existing=questionDao.findById(id);
		
		if(existing.isPresent()) {
			Question q = existing.get();
			
			q.setQuestionTitle(updatedQuestion.getQuestionTitle());
			q.setOption1(updatedQuestion.getOption1());
			q.setOption2(updatedQuestion.getOption2());
			q.setOption3(updatedQuestion.getOption3());
			q.setOption4(updatedQuestion.getOption4());
			q.setRightAnswer(updatedQuestion.getRightAnswer());
			q.setDifficultyLevel(updatedQuestion.getDifficultyLevel());
			q.setCategory(updatedQuestion.getCategory());
			
			
			questionDao.save(q);
			
			return new ResponseEntity<>("Question updated Successfully",HttpStatus.OK);
			
			
			
		}
		
		return new ResponseEntity<>("Question not found",HttpStatus.NOT_FOUND);
		
	}



	public ResponseEntity<String> deleteQuestion(Integer id) {
		if( questionDao.existsById(id)) {
			
			questionDao.deleteFromQuizQuestions(id);
			 questionDao.deleteById(id);
			 return new ResponseEntity<>("Question deleted Successfully",HttpStatus.OK);			 
		}
		
		return new ResponseEntity<>("Question not found",HttpStatus.NOT_FOUND);
	}
}
