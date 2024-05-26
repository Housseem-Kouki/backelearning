package com.example.springjwt.dto;
import java.util.List;
import lombok.Data;
@Data
public class QuizDTO {
    private int quizId;
    private String quizQuestion;
    private String quizCorrectAnswer;
    private String quizScore;
    private String quizDescription;
    private List<AnswerDTO> answers;
}
