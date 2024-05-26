package com.example.springjwt.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name= "userId")
    private long userId;
    private String name;
    private String email;
    private String phoneNumber;
    private int role;
    private String password;
    private String firstName;
    private String lastName;
    private String profession;
    private boolean status = true;;
    private String photo;

    @OneToMany(mappedBy = "teacher")
    @JsonManagedReference("teacher-course")
    private List<Course> coursesAsTeacher;

    @OneToMany(mappedBy = "student")
    @JsonManagedReference("student-stepcourse")
    private List<Stepcourse> stepcourses;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
