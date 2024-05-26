package com.example.springjwt.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.springjwt.model.Lesson;
import com.example.springjwt.model.Quiz;
import com.example.springjwt.repositories.LessonRepository;
import com.example.springjwt.repositories.QuizRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class LessonService {

	@Autowired
    private final LessonRepository lessonRepository; // Updated
    
    @Autowired
    private final QuizRepository quizRepository; // Updated
    
    public List<Lesson> getLessons() {
        return lessonRepository.findAll();
    }

    public Optional<Lesson> getLessonById(int lessonId) {
        return lessonRepository.findById(lessonId);
    }

    public Lesson save(Lesson lesson) {
    	Lesson lessonSaved =lessonRepository.save(lesson); 
         if(!lessonSaved.getQuizzes().isEmpty()) {
        	 updateQuizz(lessonSaved);
         }
         return lessonSaved;
    }

    public boolean existsById(int lessonId) {
        return lessonRepository.existsById(lessonId);
    }

    public void deleteLesson(int lessonId) {
    	lessonRepository.deleteById(lessonId);
    }
    
    public void updateQuizz(Lesson lesson) {
    	for(Quiz q :lesson.getQuizzes()) {
    		q.setLesson(lesson);
    		quizRepository.save(q);
    	}
    }
}
