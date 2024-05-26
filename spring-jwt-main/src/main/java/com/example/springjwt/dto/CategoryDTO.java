package com.example.springjwt.dto;

import java.util.List;


import lombok.Data;

@Data
public class CategoryDTO {
	private int categoryId;
	private String categoryTitle;
	private List<LessonDTO> lessons;

}
