package com.example.springjwt.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.springjwt.model.Lesson;
@Repository
public interface LessonRepository extends JpaRepository<Lesson,Integer> {
	/*   @Query("select lesson from lesson lesson left join fetch lesson.chapters where lesson.id = :lesson_id") // Updated
	   Optional<Lesson> findByIdWithChapters(@Param(("lesson_id")) int lesson_id);*/
	}
