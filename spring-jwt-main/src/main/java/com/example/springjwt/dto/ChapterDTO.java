package com.example.springjwt.dto;

import lombok.Data;

@Data
public class ChapterDTO {

	 private int chapterId;
	 private String chapterTitle;
	 private String chapterDescription;
	 private String chapterVideo;
		private int courseId;
}
