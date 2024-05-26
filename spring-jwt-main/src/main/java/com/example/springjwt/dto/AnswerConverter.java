package com.example.springjwt.dto;

import java.util.ArrayList;
import java.util.List;

import com.example.springjwt.model.Answer;
import com.example.springjwt.model.Category;
import com.example.springjwt.model.Lesson;

public class AnswerConverter {

	public static AnswerDTO toDTO(Answer entity) {
		AnswerDTO dto = new AnswerDTO();
        dto.setAnswerId(entity.getAnswerId());
        dto.setAnswer(entity.getAnswer());
        dto.setQuiz(QuizConverter.toDTO(entity.getQuiz())); 
        return dto;
    }

    public static Answer toEntity(AnswerDTO dto) {
    	Answer entity = new Answer();
        if (dto.getAnswerId() != 0) {
            entity.setAnswerId(dto.getAnswerId());
        }
        entity.setAnswer(dto.getAnswer());      
        entity.setQuiz(QuizConverter.toEntity(dto.getQuiz())); 
        return entity;
    }
}
