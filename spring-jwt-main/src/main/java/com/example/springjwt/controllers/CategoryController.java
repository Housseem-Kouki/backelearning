package com.example.springjwt.controllers;

import java.util.HashMap;
import java.util.List;

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

import com.example.springjwt.dto.CategoryConverter;
import com.example.springjwt.dto.CategoryDTO;
import com.example.springjwt.model.Category;
import com.example.springjwt.services.CategoryService;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@CrossOrigin("*")
public class CategoryController {
	
	@Autowired
    private CategoryService categoryService;
 
 @GetMapping("/categories")
    public List<Category> getCategories() {
        return categoryService.getCategories();
    } 
 @GetMapping("/categories/{categoryId}") // Updated
    public ResponseEntity<?> getCategoryById(@PathVariable int categoryId) {
        try {
        	Category foundCategory = categoryService.getCategoryById(categoryId)
                    .orElseThrow(() -> new EntityNotFoundException("Requested category not found"));
            return ResponseEntity.ok().body(foundCategory);
        } catch (EntityNotFoundException ex) {
            HashMap<String, String> message = new HashMap<>();
            message.put("message", "category not found for id: " + categoryId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
    }
    @PostMapping("/categories")
    public ResponseEntity<?> addCategory(@RequestBody CategoryDTO categoryDTO) {
        try {
            // Convert CategoryDTO to entity
        	Category category = CategoryConverter.toEntity(categoryDTO);

            // Save the category using the category service
        	Category savedCategory = categoryService.save(category);

            // Return the saved category in the response body
            return ResponseEntity.ok(savedCategory);
        } catch (Exception e) {
            // Handle any exceptions
            HashMap<String, String> message = new HashMap<>();
            message.put("message", "Failed to add category");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
        }
    }
    @PutMapping("/categories/{categoryId}") // Updated
    public ResponseEntity<?> updateCategory(@RequestBody Category category, @PathVariable(value = "categoryId") int categoryId) {
        if (categoryService.existsById(categoryId)) {
            Category existingCategory = categoryService.getCategoryById(categoryId)
                    .orElseThrow(() -> new EntityNotFoundException("Requested category not found"));
            existingCategory.setCategoryTitle(category.getCategoryTitle());
            existingCategory.setLessons(category.getLessons());
            categoryService.save(existingCategory);
            return ResponseEntity.ok().body(existingCategory);
        } else {
            HashMap<String, String> message = new HashMap<>();
            message.put("message", categoryId + " category not found or matched");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
    }
    
    @RequestMapping(method = RequestMethod.DELETE, value = "categories/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable(value = "id") int id) {   	
        if (categoryService.existsById(id)) {
        	categoryService.deleteCategory(id);
            HashMap<String, String> message = new HashMap<>();
            message.put("message", "category with id " + id + " was deleted successfully.");
            return ResponseEntity.status(HttpStatus.OK).body(message); // Updated
        } else {
            HashMap<String, String> message = new HashMap<>();
            message.put("message", id + " category not found or matched");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
    }
}
