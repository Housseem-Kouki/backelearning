package com.example.springjwt.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
public class Stepcourse {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name= "stepcourseId")
    private int stepcourseId;
    private  int etat ;

    @ManyToOne
    @JoinColumn(name = "studentId")
    @JsonBackReference("student-stepcourse")
    private  User student;

    @ManyToOne
    @JoinColumn(name = "courseId")
    @JsonBackReference
    private  Course course ;

}
