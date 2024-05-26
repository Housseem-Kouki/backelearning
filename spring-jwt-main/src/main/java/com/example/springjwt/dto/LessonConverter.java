package com.example.springjwt.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.springjwt.model.Chapter;
import com.example.springjwt.model.Lesson;
import com.example.springjwt.model.Quiz;
import com.example.springjwt.model.User;
import com.example.springjwt.repositories.CourseRepository;
import com.example.springjwt.services.CourseService;

public class LessonConverter {
    public static LessonDTO toDTO(Lesson entity) {
        LessonDTO dto = new LessonDTO();
        dto.setLessonId(entity.getLessonId());
        dto.setLessonTitle(entity.getLessonTitle());
        dto.setLessonDescription(entity.getLessonDescription());
        dto.setStatus(entity.isStatus());
        dto.setRank(entity.getRank());
        // Assuming courseId is a String in CourseDTO
        // dto.setCourseId(String.valueOf(entity.getCourse().getCourseId()));
        // You can add conversion for chapters and quizzes here if needed
        List<QuizDTO> quizs = new ArrayList<QuizDTO>();
        if(!entity.getQuizzes().isEmpty()) {
        	for(Quiz quiz: entity.getQuizzes()) {
        		quizs.add(QuizConverter.toDTO(quiz));
        	}     	
        }
        dto.setLessonQuizzes(quizs);
        List<ChapterDTO> chapters = new ArrayList<ChapterDTO>();
        /*
        if(!entity.getChapters().isEmpty()) {
        	for(Chapter chapter: entity.getChapters()) {
        		chapters.add(ChapterConverter.toDTO(chapter));
        	}     	
        }
         */
        //dto.setChapters(chapters);
        dto.setCategory(CategoryConverter.toDTO(entity.getCategory()));
        List<UserDTO> students = new ArrayList<UserDTO>();
        if(!entity.getStudents().isEmpty()) {
        	for(User user: entity.getStudents()) {
        		students.add(UserConverter.toDTO(user));
        	}     	
        }
       // dto.setStudents(students);
        //dto.setTeacher(UserConverter.toDTO(entity.getTeacher()));
        
        return dto;
    }

    public static Lesson toEntity(LessonDTO dto) {
    	Lesson entity = new Lesson();
        // Assuming courseId is a String in CourseDTO
        // entity.setCourse(CourseConverter.toEntity(dto.getCourseId()));
        if (dto.getLessonId() != 0) {
            entity.setLessonId(dto.getLessonId());
        }
        if (dto.getCourseId() != 0) {
        	
        	// existingCourse = new course(0, "cours num 1", "informatique", "description du cours", "2", 1, true, null, null, null);
        			//new course(1, "cours num 1", "informatique", "description du cours", "2", 0, true,null) ;
        	
                    
            //entity.setCourse(existingCourse);
        }
        entity.setLessonTitle(dto.getLessonTitle());
        entity.setLessonDescription(dto.getLessonDescription());
        List<Quiz> quizes = new ArrayList<Quiz>();
        if(!dto.getLessonQuizzes().isEmpty()) {
        	for(QuizDTO quizDto:dto.getLessonQuizzes()) {
        		quizes.add(QuizConverter.toEntity(quizDto));
        	}
        }
        entity.setQuizzes(quizes);        
        List<Chapter> chapters = new ArrayList<Chapter>();

        /*
        if(!dto.getChapters().isEmpty()) {
        	for(ChapterDTO chapterDto:dto.getChapters()) {
        		chapters.add(ChapterConverter.toEntity(chapterDto));
        	}
        }*/
       // entity.setChapters(chapters);
        entity.setStatus(dto.isStatus());
        entity.setRank(dto.getRank());
        entity.setCategory(CategoryConverter.toEntity(dto.getCategory()));
        return entity;
    }
}
