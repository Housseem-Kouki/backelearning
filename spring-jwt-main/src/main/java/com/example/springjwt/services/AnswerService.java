package com.example.springjwt.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.springjwt.model.Answer;
import com.example.springjwt.repositories.AnswerRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AnswerService {
	
	@Autowired
    private final AnswerRepository answerRepository; // Updated

    public List<Answer> getAnswers() {
        return answerRepository.findAll();
    }

    public Optional<Answer> getAnswerById(int answerId) {
        return answerRepository.findById(answerId);
    }

    public Answer save(Answer answer) {
        return answerRepository.save(answer); 
    }

    public boolean existsById(int answerId) {
        return answerRepository.existsById(answerId);
    }

    public void deleteAnswer(int answerId) {
    	answerRepository.deleteById(answerId);
    }

}
