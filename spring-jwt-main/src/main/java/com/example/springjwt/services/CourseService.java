package com.example.springjwt.services;

import java.util.List;
import java.util.Optional;

import com.example.springjwt.model.*;
import com.example.springjwt.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseService {
	
	
	@Autowired
    private CourseRepository courseRepository;

    @Autowired
    private IUserRepository userRepository;
	
	@Autowired
    private LessonRepository lessonRepository;
	
	@Autowired
    private QuizRepository quizRepository;
	
	@Autowired
    private ChapterRepository chapterRepository;
	
    public List<Course> getCourses() {
        return courseRepository.findAll();
    }

    public Optional<Course> getCourseById(int courseId) {
        return courseRepository.findById(courseId);
    }

    public Course save(Course course) {	
    	Course courseSaved = courseRepository.save(course);
         if(!courseSaved.getLessons().isEmpty()) {
        	 UpdateLessons(courseSaved);
         }
         return courseSaved;
    }

    public boolean existsById(int courseId) {
        return courseRepository.existsById(courseId);
    }

    public void deleteCourse(int courseId) {
    	courseRepository.deleteById(courseId);
    }
    
    public void UpdateLessons(Course course) {   	
    	for(Lesson l :course.getLessons()) {
    		l.setCourse(course);
    		lessonRepository.save(l);
    		for(Quiz q : l.getQuizzes()) {
    			q.setLesson(l);
    			quizRepository.save(q);
    		}
            /*
    		for(Chapter c : l.getChapters()) {
    			c.setLesson(l);
    			chapterRepository.save(c);
    		}

             */
    	}
       
    }

    public void suspendUser(int userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setStatus(false); // suspend the user
            userRepository.save(user);
        } else {
            System.out.println("User with ID " + userId + " not found. Unable to suspend.");
        }
    }

    public void reactivateUser(int userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setStatus(true); // reactivate the user
            userRepository.save(user);
        } else {
            System.out.println("User with ID " + userId + " not found. Unable to reactivate.");
        }
    }

}
