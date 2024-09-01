package com.example.demo.repository;

import com.example.demo.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    // Custom method to get all products sorted by ID in ascending order
    List<Product> findAllByOrderByIdAsc();
}
