package com.example.springjwt.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.springjwt.model.Chapter;
import com.example.springjwt.repositories.ChapterRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ChapterService {

	@Autowired
    private final ChapterRepository chapterRepository; // Updated

    public List<Chapter> getChapters() {
        return chapterRepository.findAll();
    }

    public Optional<Chapter> getChapterById(int chapterId) {
        return chapterRepository.findById(chapterId);
    }

    public Chapter save(Chapter chapter) {
        return chapterRepository.save(chapter); // Updated
    }

    public boolean existsById(int chapterId) {
        return chapterRepository.existsById(chapterId);
    }

    public void deleteChapter(int chapterId) {
    	chapterRepository.deleteById(chapterId);
    }
}
