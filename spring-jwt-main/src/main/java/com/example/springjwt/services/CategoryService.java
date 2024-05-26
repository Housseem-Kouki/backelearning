package com.example.springjwt.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.springjwt.model.Category;
import com.example.springjwt.repositories.CategoryRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CategoryService {

	@Autowired
    private final CategoryRepository categoryRepository; // Updated

    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    public Optional<Category> getCategoryById(int categoryId) {
        return categoryRepository.findById(categoryId);
    }

    public Category save(Category category) {
        return categoryRepository.save(category); // Updated
    }

    public boolean existsById(int categoryId) {
        return categoryRepository.existsById(categoryId);
    }

    public void deleteCategory(int categoryId) {
    	categoryRepository.deleteById(categoryId);
    }
}
