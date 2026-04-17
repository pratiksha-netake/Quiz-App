package com.QuizApp.QuizApp.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.QuizApp.QuizApp.Dao.QuestionDao;
import com.QuizApp.QuizApp.Dao.QuizDao;
import com.QuizApp.QuizApp.Model.Question;
import com.QuizApp.QuizApp.Model.QuestionWrapper;
import com.QuizApp.QuizApp.Model.Quiz;
import com.QuizApp.QuizApp.Model.Response;

@Service
public class QuizService {
	
	@Autowired
	QuizDao quizDao;
	
	@Autowired
	QuestionDao questionDao;

	public ResponseEntity<String> createQuiz(String category, int numQ, String title) {
		
		List<Question> questions = questionDao.findRandomQuestionsByCategory(category,numQ);
		Quiz quiz=new Quiz();
		quiz.setTitle(title);
		quiz.setQuestions(questions);
		quizDao.save(quiz);
		return new ResponseEntity<>("success",HttpStatus.CREATED);
	}

	public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id) {
	    Optional<Quiz> quiz = quizDao.findById(id);

	    List<Question> questionFromDB = quiz.get().getQuestions();
	    List<QuestionWrapper> questionForUser = new ArrayList<>();

	    for (Question q : questionFromDB) {
	        QuestionWrapper qw = new QuestionWrapper(
	            q.getId(),
	            q.getQuestionTitle(),
	            q.getOption1(),
	            q.getOption2(),
	            q.getOption3(),
	            q.getOption4()
	        );
	        questionForUser.add(qw);
	    }

	    return new ResponseEntity<>(questionForUser, HttpStatus.OK);
	}

	public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses) {
		Quiz quiz = quizDao.findById(id).get();
		List<Question> questions= quiz.getQuestions();
		
		int i=0;
		int right=0;
		for(Response res : responses) {
			if( res.getResponse().equals(questions.get(i).getRightAnswer())) {
			right++;
			}
			i++;
		
		}
		
		return new ResponseEntity<>(right,HttpStatus.OK);
	}

}
