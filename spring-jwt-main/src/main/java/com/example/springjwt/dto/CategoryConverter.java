package com.example.springjwt.dto;

import java.util.ArrayList;
import java.util.List;

import com.example.springjwt.model.Category;
import com.example.springjwt.model.Chapter;
import com.example.springjwt.model.Lesson;

public class CategoryConverter {
	
	public static CategoryDTO toDTO(Category entity) {
		CategoryDTO dto = new CategoryDTO();
        dto.setCategoryId(entity.getCategoryId());
        dto.setCategoryTitle(entity.getCategoryTitle());
        List<LessonDTO> lessonList= new ArrayList<LessonDTO>();
        for(Lesson l : entity.getLessons()) {
        	lessonList.add(LessonConverter.toDTO(l));
        }
        dto.setLessons(lessonList);    
        return dto;
    }

    public static Category toEntity(CategoryDTO dto) {
    	Category entity = new Category();
        if (dto.getCategoryId() != 0) {
            entity.setCategoryId(dto.getCategoryId());
        }
        entity.setCategoryTitle(dto.getCategoryTitle());
        List<Lesson> lessonList= new ArrayList<Lesson>();
        for(LessonDTO l : dto.getLessons()) {
        	lessonList.add(LessonConverter.toEntity(l));
        }
        entity.setLessons(lessonList);
        return entity;
    }

}
