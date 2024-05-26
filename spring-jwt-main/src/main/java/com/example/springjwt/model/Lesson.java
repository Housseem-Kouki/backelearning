package com.example.springjwt.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.*;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
@Table(name="lesson")
public class Lesson {
	 	@Id
	    @GeneratedValue(strategy = GenerationType.AUTO)
	    @Column(name= "lessonId")
	    private int lessonId;
	    private String lessonTitle;
	    private String lessonDescription;
	    private boolean status; 
	    private int rank;
	    @ManyToOne
	    @JoinColumn(name="courseId", nullable=true)
		@JsonIgnore
	    private Course course;	    

	    @OneToMany(mappedBy = "lesson", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	    private List<Quiz> quizzes;
	    @ManyToOne
	    @JoinColumn(name = "categoryId")
	    private Category category;
	    

	    
	    @ManyToMany
	    @JoinTable(
	        name = "lesson_student",
	        joinColumns = @JoinColumn(name = "lessonId"),
	        inverseJoinColumns = @JoinColumn(name = "studentId")
	    )
		@JsonIgnore
	    private List<User> students;

	@OneToMany(mappedBy = "lesson", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Chapter> chapters;
}
