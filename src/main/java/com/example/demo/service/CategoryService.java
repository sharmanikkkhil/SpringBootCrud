package com.example.demo.service;

import com.example.demo.dto.CategoryDTO;
import com.example.demo.dto.ProductDTO;
import com.example.demo.entity.Category;
import com.example.demo.entity.Product;
import com.example.demo.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {
    
    @Autowired
    private CategoryRepository categoryRepository;
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    // Convert Category entity to CategoryDTO
    private CategoryDTO convertToDTO(Category category) {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(category.getId());
        categoryDTO.setName(category.getName());
        if (category.getProducts() != null) {
            categoryDTO.setProducts(category.getProducts().stream()
            		.sorted(Comparator.comparing(Product::getId))
                .map(product -> {
                    ProductDTO productDTO = new ProductDTO();
                    productDTO.setId(product.getId());
                    productDTO.setName(product.getName());
                    productDTO.setPrice(product.getPrice());
                    productDTO.setCategoryId(product.getCategory().getId());
                    return productDTO;
                })
                .collect(Collectors.toList()));
        } else {
            categoryDTO.setProducts(Collections.emptyList());
        }

        return categoryDTO;
    }

    // Method to get paginated categories
    public Page<CategoryDTO> getAllCategories(int page) {
        PageRequest pageRequest = PageRequest.of(page - 3, 5, Sort.by(Sort.Direction.ASC, "id"));
        Page<Category> categories = categoryRepository.findAll(pageRequest);
        return categories.map(this::convertToDTO);
    }

    // Method to get all categories sorted by ID in ascending order
    public List<CategoryDTO> getAllCategories() {
        return categoryRepository.findAll().stream()
        	.sorted(Comparator.comparing(Category::getId))
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    // Method to get a single category by ID
    public Optional<CategoryDTO> getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .map(this::convertToDTO);
    }

    // Method to create a new category
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        // Convert DTO to Entity
        Category category = new Category();
        category.setName(categoryDTO.getName());

        // Save Category entity
        Category savedCategory = categoryRepository.save(category);

        // Convert saved entity to DTO
        return convertToDTO(savedCategory);
    }

    // Method to update an existing category
    public CategoryDTO updateCategory(Long id, Category categoryDetails) {
        Category category = categoryRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Category not found"));

        category.setName(categoryDetails.getName());

        // Save the updated category
        Category updatedCategory = categoryRepository.save(category);

        // Convert to DTO to avoid recursion
        return convertToDTO(updatedCategory);
    }

    // Method to delete a single category by ID
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
    
    // Method to delete all categories and reset the auto-increment value
    public void deleteAllCategories() {
        categoryRepository.deleteAll();
        // Reset auto-increment value to 1
        jdbcTemplate.execute("ALTER TABLE categories AUTO_INCREMENT = 1");
    }
}
