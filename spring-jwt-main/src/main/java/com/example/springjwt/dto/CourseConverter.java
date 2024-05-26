package com.example.springjwt.dto;

import java.util.ArrayList;
import java.util.List;

import com.example.springjwt.model.Course;
import com.example.springjwt.model.Lesson;

public class CourseConverter {

	public static CourseDTO toDTO(Course entity) {
		CourseDTO dto = new CourseDTO();
		dto.setCourseId(entity.getCourseId());
		dto.setCourseTitle(entity.getCourseTitle());
		dto.setCourseCategory(entity.getCourseCategory());
		dto.setCourseDescription(entity.getCourseDescription());
		dto.setCourseDuration(entity.getCourseDuration());
		dto.setCourseLevel(entity.getCourseLevel());
		dto.setCourseIsPremium(entity.getCourseIsPremium());

		dto.setAverageRating(entity.getAverageRating());  // Set average rating
		dto.setNumberOfRatings(entity.getTotalRatings());  // Set number of ratings

		List<LessonDTO> lessons = new ArrayList<>();
		if (entity.getLessons() != null && !entity.getLessons().isEmpty()) {
			for (Lesson lesson : entity.getLessons()) {
				lessons.add(LessonConverter.toDTO(lesson));
			}
		}
		dto.setLessons(lessons);

		// You can handle the coursePhotoFile conversion here if needed
		return dto;
	}

	public static Course toEntity(CourseDTO dto) {
		Course entity = new Course();
		if (dto.getCourseId() != 0) {
			entity.setCourseId(dto.getCourseId());
		}
		entity.setCourseTitle(dto.getCourseTitle());
		entity.setCourseCategory(dto.getCourseCategory());
		entity.setCourseDescription(dto.getCourseDescription());
		entity.setCourseDuration(dto.getCourseDuration());

		entity.setAverageRating(dto.getAverageRating());  // Set average rating
		entity.setTotalRatings(dto.getNumberOfRatings());  // Set number of ratings


		List<Lesson> lessons = new ArrayList<>();
		if (dto.getLessons() != null && !dto.getLessons().isEmpty()) {
			for (LessonDTO lessonDto : dto.getLessons()) {
				if (dto.getCourseId() != 0) {
					lessonDto.setCourseId(dto.getCourseId());
				}
				lessons.add(LessonConverter.toEntity(lessonDto));
			}
		}
		entity.setLessons(lessons);

		entity.setCourseLevel(dto.getCourseLevel());
		entity.setCourseIsPremium(dto.isCourseIsPremium());

		// You can handle the coursePhotoFile conversion here if needed
		return entity;
	}
}
