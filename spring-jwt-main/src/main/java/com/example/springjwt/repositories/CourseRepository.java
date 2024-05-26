package com.example.springjwt.repositories;

import com.example.springjwt.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.springjwt.model.Course;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course,Integer>{
public List<Course> findCourseByTeacher(User teacher);
}
