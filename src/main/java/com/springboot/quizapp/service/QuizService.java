package com.springboot.quizapp.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.quizapp.dao.QuestionRepository;
import com.springboot.quizapp.dao.QuizDao;
import com.springboot.quizapp.model.Question;
import com.springboot.quizapp.model.QuestionWrapper;
import com.springboot.quizapp.model.Quiz;
import com.springboot.quizapp.model.Response;

@Service
public class QuizService {
	
	@Autowired
	QuizDao quizDao;
	
	@Autowired
	QuestionRepository questionDao;

	public Quiz createQuiz(String category, int numQ, String title) {
		// TODO Auto-generated method stub
		
		Quiz quiz = new Quiz();
		quiz.setTitle(title);
		
		
		// now to set multiple questions in one quiz and questions can be repeated in another quiz we have many to many relationship
		// to we need to first set listOfQuestions in that particular quiz
		
		List<Question> questions = questionDao.findRandomQuestionsByCategory(category, numQ);
		
		quiz.setQuestions(questions);
		
		// saving quiz
		return quizDao.save(quiz);  // this method first set id to 1 which is auto increment , then set title then as it has mapping so take that id as foreign key and add list of questions to mapping table 
		 
	}

	
	public List<QuestionWrapper> getQuizQuestions(int quizId) {
		
		Optional<Quiz> quiz;
	
		
		quiz = quizDao.findById(quizId);  // this will return (id,title) based on id
		
		//System.out.println(quiz);
		System.out.println(quiz.get());   // here quiz.get() will internally perform join operation and create object of quiz class and return that object ..  that's again coolest thing with java springboot
		
		
		if(!quiz.isPresent())  /// check is it present
		return null;
		
		List<Question> questionFromDb = quiz.get().getQuestions();  // this will return detailed information of all questions
		List<QuestionWrapper> questionsForUsers = new ArrayList<>();  // this will contains only those details which is needed 
		
		for(Question question : questionFromDb)  // wrapping our questions for users
		{
			QuestionWrapper questionWrapper = new QuestionWrapper(question.getId(),question.getOption1(),question.getOption2(),question.getOption3(),question.getOption4(),question.getQuestion_title());
			questionsForUsers.add(questionWrapper);
		}
	
		
		return questionsForUsers;
	
	}


	public int calculateResult(int quizId, List<Response> responses) {
		
		Quiz quiz = new Quiz();
		
		Optional<Quiz> quizOptional = this.quizDao.findById(quizId);
		
		quiz = quizOptional.get();
		
		int result = 0 ;
		
		List<Question> correctAnsweredQuestions = quiz.getQuestions();
		
		Collections.sort(correctAnsweredQuestions,(a,b)->a.getId()>b.getId()?1:-1);  //peform sorting based on id
		
		responses.sort((a,b)->a.getId()>b.getId()?1:-1);  // so that user by mistakely send reponse on different order then also we are able to provide correct answers
		
//		System.out.println("List of correct answers : "+correctAnsweredQuestions);
//		System.out.println("List of responses : "+responses);
		 int temp = 0;
		 
		for(Response response : responses)
		{
			System.out.println("responses : "+response);
			if(response.getId() == correctAnsweredQuestions.get(temp).getId()) // that means we are checking correct question
			{
				if(response.getResponse().equals(correctAnsweredQuestions.get(temp).getRight_answer()))
					result++;
			}
			temp++;
		}
		
		System.out.println("Result is : "+result);
		
		return result;
		
	}

	 
}
