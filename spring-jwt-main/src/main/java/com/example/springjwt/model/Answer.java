package com.example.springjwt.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
public class Answer {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name= "answerId")
    private int answerId;
    private String answer;
    private boolean correct;
    @ManyToOne
    @JoinColumn(name = "quizId")
    @JsonIgnore
    private Quiz quiz;

}
