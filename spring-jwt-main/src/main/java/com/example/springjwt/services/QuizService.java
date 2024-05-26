package com.example.springjwt.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.springjwt.model.Quiz;
import com.example.springjwt.repositories.QuizRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class QuizService {
	@Autowired
    private final QuizRepository quizRepository; // Updated

    public List<Quiz> getQuizzes() {
        return quizRepository.findAll();
    }

    public Optional<Quiz> getQuizById(int quizId) {
        return quizRepository.findById(quizId);
    }

    public Quiz save(Quiz quiz) {
        return quizRepository.save(quiz); // Updated
    }

    public boolean existsById(int quizId) {
        return quizRepository.existsById(quizId);
    }

    public void deleteQuiz(int quizId) {
    	quizRepository.deleteById(quizId);
    }
	
}
