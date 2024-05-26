package com.example.springjwt.controllers;

import java.util.HashMap;
import java.util.List;

import com.example.springjwt.model.Course;
import com.example.springjwt.repositories.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.springjwt.dto.ChapterConverter;
import com.example.springjwt.dto.ChapterDTO;
import com.example.springjwt.model.Chapter;
import com.example.springjwt.services.ChapterService;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/rest/chapter")
public class ChapterController {
	private CourseRepository courseRepository;
	 @Autowired
	    private ChapterService chapterService;
	 
	 @GetMapping("/chapters")
	    public List<Chapter> getChapters() {
	        return chapterService.getChapters();
	    } 
	 @GetMapping("/chapters/{chapterId}") // Updated
	    public ResponseEntity<?> getChapterById(@PathVariable int chapterId) {
	        try {
	        	Chapter foundChapter = chapterService.getChapterById(chapterId)
	                    .orElseThrow(() -> new EntityNotFoundException("Requested chapter not found"));
	            return ResponseEntity.ok().body(foundChapter);
	        } catch (EntityNotFoundException ex) {
	            HashMap<String, String> message = new HashMap<>();
	            message.put("message", "Chapter not found for id: " + chapterId);
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
	        }
	    }

	    @PostMapping("/addChapter/{courseId}")
	    public Chapter addChapter(@RequestBody ChapterDTO chapterDTO , @PathVariable(value = "courseId")int courseId) {

			Course course = courseRepository.findById(courseId).orElse(null);
	        Chapter chapter = new Chapter();
			//chapter.setCourse(course);

			return chapter;
	    }

	    @PutMapping("/chapters/{chapterId}") // Updated
	    public ResponseEntity<?> updateChapter(@RequestBody Chapter chapter, @PathVariable(value = "chapterId") int chapterId) {
	        if (chapterService.existsById(chapterId)) {
	            Chapter existingChapter = chapterService.getChapterById(chapterId)
	                    .orElseThrow(() -> new EntityNotFoundException("Requested chapter not found"));
	            existingChapter.setChapterTitle(chapter.getChapterTitle());
	            existingChapter.setChapterDescription(chapter.getChapterDescription());
	            existingChapter.setChapterVideo(chapter.getChapterVideo());
	            //existingChapter.setLesson(chapter.getLesson());
	            chapterService.save(existingChapter);
	            return ResponseEntity.ok().body(existingChapter);
	        } else {
	            HashMap<String, String> message = new HashMap<>();
	            message.put("message", chapterId + " chapter not found or matched");
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
	        }
	    }
	   
	    
	    @RequestMapping(method = RequestMethod.DELETE, value = "chapters/{id}")
	    public ResponseEntity<?> deleteChapter(@PathVariable(value = "id") int id) {   	
	        if (chapterService.existsById(id)) {
	            chapterService.deleteChapter(id);
	            HashMap<String, String> message = new HashMap<>();
	            message.put("message", "Chapter with id " + id + " was deleted successfully.");
	            return ResponseEntity.status(HttpStatus.OK).body(message); // Updated
	        } else {
	            HashMap<String, String> message = new HashMap<>();
	            message.put("message", id + " chapter not found or matched");
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
	        }
	    }
	
}
