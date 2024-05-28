package com.example.springjwt.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User implements Serializable {
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
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date birthdate;
    private String country;
    private String location;
    private boolean status = true;;
    private String photo;

    @JsonIgnore
    @Lob
    @Column(length = 100000)
    private byte[] userPhotoFile;
    private String fileName;

    @OneToMany(mappedBy = "teacher")
    @JsonIgnoreProperties("teacher")
    private List<Course> coursesAsTeacher;

    @OneToMany(mappedBy = "student")
    @JsonManagedReference ("student-stepcourse")
    private List<Stepcourse> stepcourses;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
