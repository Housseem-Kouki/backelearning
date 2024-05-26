package com.example.springjwt.dto;

import java.util.List;

import lombok.Data;
@Data
public class CourseDTO {
	   private int courseId;
	    private String courseTitle;
	    private String courseCategory;
	    private String courseDescription;
	    private String courseDuration;
	    private int courseLevel;
	    private boolean courseIsPremium;
	    private List<LessonDTO> lessons;
	private List<UserDTO> students;


	private double averageRating;
	private int numberOfRatings;

}
