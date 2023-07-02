package com.springboot.quizapp.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.quizapp.model.Quiz;

public interface QuizDao extends JpaRepository<Quiz, Integer> {

}
