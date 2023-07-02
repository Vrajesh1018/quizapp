package com.springboot.quizapp.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.springboot.quizapp.dao.QuestionRepository;
import com.springboot.quizapp.model.Question;

@Component
public class QuestionService {

	@Autowired
	private QuestionRepository questionRepository;
	
	
	public List<Question> getAllQuestions()
	{
		List<Question> listOfQuestions = new ArrayList<>();
		Iterable<Question> itr = this.questionRepository.findAll();
		itr.forEach(q->listOfQuestions.add(q));
		
		return listOfQuestions;
	}
	
	
	public List<Question> getQuestionsByCategory(String category)
	{
		return this.questionRepository.findByCategory(category);
	}
	
	public Question addQuestion(Question question)
	{
		return this.questionRepository.save(question);
	}
}
