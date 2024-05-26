package com.example.springjwt.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.*;
import lombok.Getter;
import lombok.Setter;
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
public class Chapter {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name= "chapterId")
    private int chapterId;
    private String chapterTitle;
    private String chapterDescription;
    private String chapterVideo;


    @ManyToOne
    @JoinColumn(name="lessonId", nullable=true)
    @JsonIgnore
    private Lesson lesson;
}
