package com.example.demo.service;

import com.example.demo.dto.ProductDTO;
import com.example.demo.entity.Product;
import com.example.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    // Convert Product entity to ProductDTO
    private ProductDTO convertToDTO(Product product) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setPrice(product.getPrice());
        if (product.getCategory() != null) {
            productDTO.setCategoryId(product.getCategory().getId());
            productDTO.setCategoryName(product.getCategory().getName()); // Ensure this field is set in ProductDTO
        } else {
            productDTO.setCategoryId(null);
            productDTO.setCategoryName(null);
        }
        return productDTO;
    }

    // Method to get paginated products
    public Page<ProductDTO> getAllProducts(int page) {
        Page<Product> products = productRepository.findAll(PageRequest.of(page - 2, 5)); // 5 items per page
        return products.map(this::convertToDTO);
    }

    // Method to get all products sorted by ID in ascending order
    public List<ProductDTO> getAllProducts() {
        return productRepository.findAllByOrderByIdAsc().stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    // Method to get a single product by ID
    public Optional<ProductDTO> getProductById(Long id) {
        return productRepository.findById(id)
                .map(this::convertToDTO); // Convert the Product entity to ProductDTO
    }

    // Method to create a new product
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    // Method to update an existing product
    public Product updateProduct(Long id, Product productDetails) {
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Product not found"));

        product.setName(productDetails.getName());
        product.setPrice(productDetails.getPrice());
        product.setCategory(productDetails.getCategory()); // Ensure proper category setting
        return productRepository.save(product);
    }

    // Method to delete a single product by ID
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    // Method to delete all products and reset the auto-increment value
    public void deleteAllProducts() {
        productRepository.deleteAll();
        // Reset auto-increment value to 1
        jdbcTemplate.execute("ALTER TABLE products AUTO_INCREMENT = 1");
    }
}
