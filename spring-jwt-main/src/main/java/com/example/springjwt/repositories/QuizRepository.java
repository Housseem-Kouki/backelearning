package com.example.springjwt.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.springjwt.model.Quiz;
@Repository
public interface QuizRepository extends JpaRepository<Quiz,Integer>{

}
