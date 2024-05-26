package com.example.springjwt.dto;



import lombok.Data;

@Data
public class AnswerDTO {
	 private int answerId;
	 private String answer;
	private String text;
	 private boolean correct;
	 private QuizDTO quiz;
}
