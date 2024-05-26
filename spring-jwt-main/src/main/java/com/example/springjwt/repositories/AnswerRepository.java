package com.example.springjwt.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.springjwt.model.Answer;
@Repository
public interface AnswerRepository extends JpaRepository<Answer, Integer>{

}
