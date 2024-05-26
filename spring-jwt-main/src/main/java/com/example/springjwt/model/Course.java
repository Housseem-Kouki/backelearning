package com.example.springjwt.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "courses")
public class Course {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "courseId")
	private int courseId;
	private String courseTitle;
	private String courseCategory;
	private String courseDescription;
	private String courseDuration;
	private int courseLevel;
	private boolean status = true;
	private boolean courseIsPremium;
	private String fileName;

	@JsonIgnore
	@Lob
	@Column(length = 100000)
	private byte[] coursePhotoFile;

	public boolean getCourseIsPremium() {
		return this.courseIsPremium;
	}

	@Column(name = "course_pub_date")
	private LocalDateTime coursePubDate;

	@OneToMany(mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Lesson> lessons;

	@ManyToOne
	@JoinColumn(name = "teacherId")
	@JsonBackReference("teacher-course")
	private User teacher;
}
