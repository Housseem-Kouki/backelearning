package com.example.springjwt.controllers;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springjwt.dto.QuizConverter;
import com.example.springjwt.dto.QuizDTO;
import com.example.springjwt.model.Quiz;
import com.example.springjwt.services.QuizService;

import jakarta.persistence.EntityNotFoundException;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1")
public class Quizcontroller {

	 @Autowired
	    private QuizService quizService;

	    @GetMapping("/quizzes")
	    public List<Quiz> getQuizzes() {
	        return quizService.getQuizzes();
	    }

	    @GetMapping("/quizzes/{quizId}")
	    public ResponseEntity<?> getQuizById(@PathVariable int quizId) {
	        try {
	            Quiz foundQuiz = quizService.getQuizById(quizId)
	                    .orElseThrow(() -> new EntityNotFoundException("Requested quiz not found"));
	            return ResponseEntity.ok().body(foundQuiz);
	        } catch (EntityNotFoundException ex) {
	            HashMap<String, String> message = new HashMap<>();
	            message.put("message", "Quiz not found for id: " + quizId);
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
	        }
	    }

	    @PostMapping(path = "/quizzes")
	    public ResponseEntity<?> addQuiz(@RequestBody QuizDTO quizDTO) {
	        try {
	            // Convert QuizDTO to quiz entity
	            Quiz quiz = QuizConverter.toEntity(quizDTO);
	            
	            // Save the quiz entity using the quiz service
	            Quiz savedQuiz = quizService.save(quiz);
	            
	            // Return ResponseEntity with the saved quiz
	            return ResponseEntity.ok(savedQuiz);
	        } catch (Exception e) {
	            // Handle any exceptions and return an appropriate response
	            e.printStackTrace();
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add quiz");
	        }
	    }


	    @PutMapping("/quizzes/{quizId}")
	    public ResponseEntity<?> updateQuiz(@RequestBody Quiz quiz, @PathVariable int quizId) {
	        if (quizService.existsById(quizId)) {
	            Quiz existingQuiz = quizService.getQuizById(quizId)
	                    .orElseThrow(() -> new EntityNotFoundException("Requested quiz not found"));
	            // Update the existing quiz fields
	            existingQuiz.setQuizQuestion(quiz.getQuizQuestion());
	            existingQuiz.setQuizCorrectAnswer(quiz.getQuizCorrectAnswer());
	            existingQuiz.setQuizScore(quiz.getQuizScore());
	            existingQuiz.setLesson(quiz.getLesson());
	            quizService.save(existingQuiz);
	            return ResponseEntity.ok().body(existingQuiz);
	        } else {
	            HashMap<String, String> message = new HashMap<>();
	            message.put("message", quizId + " quiz not found or matched");
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
	        }
	    }


	    @DeleteMapping("/quizzes/{quizId}")
	    public ResponseEntity<?> deleteQuiz(@PathVariable int quizId) {
	        if (quizService.existsById(quizId)) {
	        	quizService.deleteQuiz(quizId);
	            HashMap<String, String> message = new HashMap<>();
	            message.put("message", "Quiz with id " + quizId + " was deleted successfully.");
	            return ResponseEntity.status(HttpStatus.OK).body(message);
	        } else {
	            HashMap<String, String> message = new HashMap<>();
	            message.put("message", quizId + " quiz not found or matched");
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
	        }
	    }
}
