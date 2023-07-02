package com.springboot.quizapp.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.springboot.quizapp.model.Question;

public interface QuestionRepository  extends CrudRepository<Question, Integer>{

	// one of the coolest things of spring boot databse . as in databse we have column Category so spring boot
	// is smart enough that user want to find based on category so it will it self fire query and use category in where clause  this is one of the coolest thing of spring boot
	List<Question> findByCategory(String category);

	@Query(value = "SELECT * FROM question q where q.category=:category ORDER BY RAND() LIMIT :numQ",nativeQuery = true)
	List<Question> findRandomQuestionsByCategory(String category, int numQ);
}
