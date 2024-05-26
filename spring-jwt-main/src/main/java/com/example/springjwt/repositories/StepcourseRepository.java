package com.example.springjwt.repositories;

import com.example.springjwt.model.Course;
import com.example.springjwt.model.Quiz;
import com.example.springjwt.model.Stepcourse;
import com.example.springjwt.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StepcourseRepository extends JpaRepository<Stepcourse,Integer> {
    boolean existsByStudentAndCourse(User student, Course course);
    Stepcourse findByStudentAndCourse(User student, Course course);
    List<Stepcourse> findByStudent(User user);

    Stepcourse findByCourseAndStudent(Course course , User student);


    @Query("SELECT COUNT(sc) FROM Stepcourse sc WHERE sc.course.courseId = :courseId")
    int countStudentsByCourseId(@Param("courseId") int courseId);
}
