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
import org.springframework.web.bind.annotation.RestController;

import com.springboot.quizapp.model.Question;
import com.springboot.quizapp.service.QuestionService;

@RestController
@RequestMapping("question")
public class QuestionController {

	@Autowired
	private QuestionService questionService;
	
	@GetMapping("/allquestions")
	public ResponseEntity<List<Question>> getAllQuestions() {
		
		List<Question> allQuestions = this.questionService.getAllQuestions();
		
		if(allQuestions.size() == 0)
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		
		return ResponseEntity.of(Optional.of(allQuestions));
	}
	
	@GetMapping("/category/{categoryValue}")
	public ResponseEntity<List<Question>>  getQuestionByCategory(@PathVariable("categoryValue") String category)
	{
		List<Question> questionsByCategory = this.questionService.getQuestionsByCategory(category);
		
		if(questionsByCategory.size() == 0)
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		
		return ResponseEntity.of(Optional.of(questionsByCategory));
	}
	
	@PostMapping("/add")
	public ResponseEntity<Question> addQuestion(@RequestBody Question question)
	{
		Question saveQuestion = this.questionService.addQuestion(question);
		
		return ResponseEntity.of(Optional.of(saveQuestion));
	}
	
}
