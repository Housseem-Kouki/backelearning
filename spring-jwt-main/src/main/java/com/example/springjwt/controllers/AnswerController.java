package com.example.springjwt.controllers;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.springjwt.dto.AnswerConverter;
import com.example.springjwt.dto.AnswerDTO;
import com.example.springjwt.dto.CategoryConverter;
import com.example.springjwt.dto.CategoryDTO;
import com.example.springjwt.model.Answer;
import com.example.springjwt.model.Category;
import com.example.springjwt.services.AnswerService;
import com.example.springjwt.services.CategoryService;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@CrossOrigin("*")
public class AnswerController {
	@Autowired
    private AnswerService answerService;
 
 @GetMapping("/answers")
    public List<Answer> getAnswers() {
        return answerService.getAnswers();
    } 
 @GetMapping("/answers/{answerId}") // Updated
    public ResponseEntity<?> getAnswerById(@PathVariable int answerId) {
        try {
        	Answer foundAnswer = answerService.getAnswerById(answerId)
                    .orElseThrow(() -> new EntityNotFoundException("Requested answer not found"));
            return ResponseEntity.ok().body(foundAnswer);
        } catch (EntityNotFoundException ex) {
            HashMap<String, String> message = new HashMap<>();
            message.put("message", "answer not found for id: " + answerId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
    }

    @PostMapping("/answers")
    public ResponseEntity<?> addAnswer(@RequestBody AnswerDTO answerDTO) {
        try {
            // Convert AnswerDTO to entity
        	Answer answer = AnswerConverter.toEntity(answerDTO);

            // Save the answer using the answer service
        	Answer savedAnswer = answerService.save(answer);

            // Return the saved category in the response body
            return ResponseEntity.ok(savedAnswer);
        } catch (Exception e) {
            // Handle any exceptions
            HashMap<String, String> message = new HashMap<>();
            message.put("message", "Failed to add Answer");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
        }
    }

    @PutMapping("/answers/{answerId}") // Updated
    public ResponseEntity<?> updateAnswer(@RequestBody Answer answer, @PathVariable(value = "answerId") int answerId) {
        if (answerService.existsById(answerId)) {
            Answer existingAnswer = answerService.getAnswerById(answerId)
                    .orElseThrow(() -> new EntityNotFoundException("Requested category not found"));
            existingAnswer.setAnswer(answer.getAnswer());
            existingAnswer.setCorrect(answer.isCorrect());
            existingAnswer.setQuiz(answer.getQuiz());
            answerService.save(existingAnswer);
            return ResponseEntity.ok().body(existingAnswer);
        } else {
            HashMap<String, String> message = new HashMap<>();
            message.put("message", answerId + " answer not found or matched");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
    }
    @RequestMapping(method = RequestMethod.DELETE, value = "answers/{id}")
    public ResponseEntity<?> deleteAnswer(@PathVariable(value = "id") int id) {   	
        if (answerService.existsById(id)) {
        	answerService.deleteAnswer(id);
            HashMap<String, String> message = new HashMap<>();
            message.put("message", "answer with id " + id + " was deleted successfully.");
            return ResponseEntity.status(HttpStatus.OK).body(message); // Updated
        } else {
            HashMap<String, String> message = new HashMap<>();
            message.put("message", id + " answer not found or matched");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
    }

}
