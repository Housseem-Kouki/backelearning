package com.example.springjwt.controllers;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.springjwt.model.Course;
import com.example.springjwt.model.Lesson;
import com.example.springjwt.services.CourseService;
import com.example.springjwt.services.LessonService;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/rest/lesson")
public class LessonController {

	@Autowired
    private LessonService lessonService;

    @Autowired
    private CourseService courseService;
    
    @GetMapping("/lessons")
    public List<Lesson> getLessons() {
        return lessonService.getLessons();
    }

    @GetMapping("/lessons/{lessonId}")
    public ResponseEntity<?> getLessonById(@PathVariable int lessonId) {
        try {
            Lesson foundLesson = lessonService.getLessonById(lessonId)
                    .orElseThrow(() -> new EntityNotFoundException("Requested lesson not found"));
            return ResponseEntity.ok().body(foundLesson);
        } catch (EntityNotFoundException ex) {
            HashMap<String, String> message = new HashMap<>();
            message.put("message", "Lesson not found for id: " + lessonId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
    }

    @PostMapping("/lessons/{courseId}")
    public Lesson addLesson(@RequestBody Lesson lesson, @PathVariable(value = "courseId") int courseId) {
        // Fetch the course by courseId
        Course course = courseService.getCourseById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("Requested course not found"));
        
        // Set the course in the lesson
        lesson.setCourse(course);
        
        return lessonService.save(lesson);
    }

    @PutMapping("/lessons/{lessonId}")
    public ResponseEntity<?> updateLesson(@RequestBody Lesson lesson, @PathVariable int lessonId) {
        if (lessonService.existsById(lessonId)) {
            Lesson existingLesson = lessonService.getLessonById(lessonId)
                    .orElseThrow(() -> new EntityNotFoundException("Requested lesson not found"));
           // existingLesson.setChapters(lesson.getChapters());
            existingLesson.setQuizzes(lesson.getQuizzes());
            existingLesson.setLessonTitle(lesson.getLessonTitle());
            existingLesson.setLessonDescription(lesson.getLessonDescription());
            lessonService.save(existingLesson);
            return ResponseEntity.ok().body(existingLesson);
        } else {
            HashMap<String, String> message = new HashMap<>();
            message.put("message", lessonId + " lesson not found or matched");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
    }

    @DeleteMapping("/lessons/{lessonId}")
    public ResponseEntity<?> deleteLesson(@PathVariable int lessonId) {
        if (lessonService.existsById(lessonId)) {
            lessonService.deleteLesson(lessonId);
            HashMap<String, String> message = new HashMap<>();
            message.put("message", "Lesson with id " + lessonId + " was deleted successfully.");
            return ResponseEntity.status(HttpStatus.OK).body(message);
        } else {
            HashMap<String, String> message = new HashMap<>();
            message.put("message", lessonId + " lesson not found or matched");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
    }
}
