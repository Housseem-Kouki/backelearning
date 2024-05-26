package com.example.springjwt.dto;

import com.example.springjwt.model.Chapter;

public class ChapterConverter {

	 public static ChapterDTO toDTO(Chapter entity) {
	        ChapterDTO dto = new ChapterDTO();
	        dto.setChapterId(entity.getChapterId());
	        dto.setChapterTitle(entity.getChapterTitle());
	        dto.setChapterDescription(entity.getChapterDescription());
	        dto.setChapterVideo(entity.getChapterVideo());
	        return dto;
	    }

	    public static Chapter toEntity(ChapterDTO dto) {
	    	Chapter entity = new Chapter();
	        if (dto.getChapterId() != 0) {
	            entity.setChapterId(dto.getChapterId());
	        }
	        entity.setChapterTitle(dto.getChapterTitle());
	        entity.setChapterDescription(dto.getChapterDescription());
	        entity.setChapterVideo(dto.getChapterVideo());
	        return entity;
	    }
}
