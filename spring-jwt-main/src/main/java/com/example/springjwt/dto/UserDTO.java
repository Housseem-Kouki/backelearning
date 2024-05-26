package com.example.springjwt.dto;

import java.util.List;

import com.example.springjwt.model.Lesson;

import lombok.Data;

@Data
public class UserDTO {
	private long userId;
	private String name;
	private String phoneNumber;
	private int role;
	private String email;
	private String password;
	private String profession;
	private List<CourseDTO> coursesAsStudent;
	private List<LessonDTO> lessonsAsTeacher;
	private boolean status; 
	private String photo; 
	   
}
