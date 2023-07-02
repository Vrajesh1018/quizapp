package com.springboot.quizapp.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.quizapp.model.Question;
import com.springboot.quizapp.model.QuestionWrapper;
import com.springboot.quizapp.model.Quiz;
import com.springboot.quizapp.model.Response;
import com.springboot.quizapp.service.QuizService;

@RestController
@RequestMapping("/quiz")
public class QuizController {
	
	@Autowired
	QuizService quizService;

	@GetMapping("/create")
	public ResponseEntity<String> createQuiz(@RequestParam String category,@RequestParam int numQ,  @RequestParam String title)
	{
		Quiz createdQuiz =  quizService.createQuiz(category,numQ,title);
		
//		System.out.println(category+" "+numQ+" "+title);
		if(createdQuiz != null)
		return new ResponseEntity<>("From Quiz controlller" ,HttpStatus.CREATED);
		else {
			return new ResponseEntity<>("From Quiz Controller",HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/get/{quizId}")
	public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(@PathVariable("quizId") int quizId)
	{
		
		  //System.out.println(quizId);
		
		 List<QuestionWrapper> questionWrappers =  quizService.getQuizQuestions(quizId);
	
		 if(questionWrappers == null) 
			 return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		 
		 return ResponseEntity.of(Optional.of(questionWrappers));
		
	}
	
	@PostMapping("/submit/{quizId}")
	public ResponseEntity<Integer> submitQuiz(@PathVariable("quizId") int quizId,@RequestBody List<Response> responses)
	{
		int finalResult = quizService.calculateResult(quizId,responses);
		
		return ResponseEntity.of(Optional.of(finalResult));
	}
}
