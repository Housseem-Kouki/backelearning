package com.example.springjwt.dto;

import java.util.ArrayList;
import java.util.List;

import com.example.springjwt.model.Answer;
import com.example.springjwt.model.Quiz;

public class QuizConverter {
    public static QuizDTO toDTO(Quiz entity) {
        QuizDTO dto = new QuizDTO();
        dto.setQuizId(entity.getQuizId());
        dto.setQuizQuestion(entity.getQuizQuestion());
        dto.setQuizCorrectAnswer(entity.getQuizCorrectAnswer());
        dto.setQuizScore(String.valueOf(entity.getQuizScore()));
        List<AnswerDTO> listAnswer = new ArrayList<AnswerDTO>();
        for(Answer a:entity.getAnswers()) {
        	listAnswer.add(AnswerConverter.toDTO(a));
        }
        dto.setAnswers(listAnswer);
        return dto;
    }

    public static Quiz toEntity(QuizDTO dto) {
    	Quiz entity = new Quiz();
        if (dto.getQuizId() !=0) {
            entity.setQuizId(dto.getQuizId());
        }
        entity.setQuizQuestion(dto.getQuizQuestion());
        entity.setQuizCorrectAnswer(dto.getQuizCorrectAnswer());
        entity.setQuizScore(Integer.parseInt(dto.getQuizScore()));
        List<Answer> listAnswer = new ArrayList<Answer>();
        for(AnswerDTO a:dto.getAnswers()) {
        	listAnswer.add(AnswerConverter.toEntity(a));
        }
        entity.setAnswers(listAnswer);
        return entity;
    }
}
