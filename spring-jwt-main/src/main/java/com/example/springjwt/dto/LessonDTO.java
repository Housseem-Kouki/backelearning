package com.example.springjwt.dto;

import java.util.ArrayList;
import java.util.List;


import lombok.Data;
@Data
public class LessonDTO {
	  	private int lessonId;
	    private String lessonTitle;
	    private String lessonDescription;
	    private int courseId; // Assuming courseId is a String in CourseDTO
	private List<QuizDTO> lessonQuizzes = new ArrayList<>(); // Initialisation
	private List<ChapterDTO> lessonChapters = new ArrayList<>(); // Initialisation
	    private CategoryDTO category;
	    private UserDTO teacher;

	    private boolean status; 
	    private int rank;
}
