package com.example.springjwt.model;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.Getter;
import lombok.Setter;
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
public class Quiz {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name= "quizId")
    private int quizId;
    private String quizQuestion;
    private String quizCorrectAnswer;
    private int quizScore;
    private String quizDescription;
    @ManyToOne
    @JoinColumn(name = "lessonId",nullable=true)
    @JsonIgnore
    private Lesson lesson;


    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Answer> answers;

}
