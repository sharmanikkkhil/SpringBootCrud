package com.example.demo.controller;

import com.example.demo.dto.CategoryDTO;
import com.example.demo.entity.Category;
import com.example.demo.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    // Method to get paginated categories
    @GetMapping
    public ResponseEntity<?> getAllCategories(@RequestParam(defaultValue = "3") int page, 
                                              @RequestParam(defaultValue = "false") boolean all) {
        if (all) {
            // If the 'all' parameter is true, return all categories sorted by ID
            return ResponseEntity.ok(categoryService.getAllCategories());
        } else {
            // Otherwise, return paginated categories
            return ResponseEntity.ok(categoryService.getAllCategories(page));
        }
    }

    // Method to get a single category by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable Long id) {
        return categoryService.getCategoryById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    // Method to create a new category
    @PostMapping
    public ResponseEntity<CategoryDTO> createCategory(@RequestBody CategoryDTO categoryDTO) {
        CategoryDTO createdCategory = categoryService.createCategory(categoryDTO);
        return ResponseEntity.ok(createdCategory);
    }

    // Method to update an existing category
    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> updateCategory(@PathVariable Long id, @RequestBody Category categoryDetails) {
        CategoryDTO updatedCategoryDTO = categoryService.updateCategory(id, categoryDetails);
        return ResponseEntity.ok(updatedCategoryDTO);
    }

    // Method to delete a single category by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
    
    // Method to delete all categories
    @DeleteMapping("/all")
    public ResponseEntity<?> deleteAllCategories() {
        categoryService.deleteAllCategories();
        return ResponseEntity.noContent().build();
    }
}
