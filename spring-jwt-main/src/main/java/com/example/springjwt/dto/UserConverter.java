package com.example.springjwt.dto;

import java.util.ArrayList;
import java.util.List;

import com.example.springjwt.model.Course;
import com.example.springjwt.model.Lesson;
import com.example.springjwt.model.Stepcourse;
import com.example.springjwt.model.User;

public class UserConverter {

    public static UserDTO toDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setUserId(user.getUserId());
        dto.setName(user.getName());
        dto.setPassword(user.getPassword());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole());
        dto.setProfession(user.getProfession());
        dto.setStatus(user.isStatus());
        dto.setPhoto(user.getPhoto());

        List<CourseDTO> coursesAsStudent = new ArrayList<>();
        if (user.getStepcourses() != null && !user.getStepcourses().isEmpty()) {
            for (Stepcourse stepcourse : user.getStepcourses()) {
                coursesAsStudent.add(CourseConverter.toDTO(stepcourse.getCourse()));
            }
        }
        dto.setCoursesAsStudent(coursesAsStudent);

        List<LessonDTO> lessonsAsTeacher = new ArrayList<>();
        /*
        if (user.getLessonsAsTeacher() != null && !user.getLessonsAsTeacher().isEmpty()) {
            for (Lesson lesson : user.getLessonsAsTeacher()) {
                lessonsAsTeacher.add(LessonConverter.toDTO(lesson));
            }
        }

         */
        dto.setLessonsAsTeacher(lessonsAsTeacher);

        return dto;
    }

    public static User toEntity(UserDTO dto) {
        User entity = new User();
        if (dto.getUserId() != 0) {
            entity.setUserId(dto.getUserId());
        }
        entity.setEmail(dto.getEmail());
        entity.setName(dto.getName());
        entity.setPassword(dto.getPassword());
        entity.setPhoneNumber(dto.getPhoneNumber());
        entity.setRole(dto.getRole());
        entity.setProfession(dto.getProfession());
        entity.setStatus(dto.isStatus());
        entity.setPhoto(dto.getPhoto());

        List<CourseDTO> coursesAsStudent = new ArrayList<>();
        if (entity.getStepcourses() != null && !entity.getStepcourses().isEmpty()) {
            for (Stepcourse stepcourse : entity.getStepcourses()) {
                coursesAsStudent.add(CourseConverter.toDTO(stepcourse.getCourse()));
            }
        }
        dto.setCoursesAsStudent(coursesAsStudent);

        List<Lesson> lessonsAsTeacher = new ArrayList<>();
        if (dto.getLessonsAsTeacher() != null && !dto.getLessonsAsTeacher().isEmpty()) {
            for (LessonDTO lesson : dto.getLessonsAsTeacher()) {
                lessonsAsTeacher.add(LessonConverter.toEntity(lesson));
            }
        }
       // entity.setLessonsAsTeacher(lessonsAsTeacher);

        return entity;
    }
}