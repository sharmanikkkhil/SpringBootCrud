package com.example.demo.repository;

import com.example.demo.entity.Category;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
	List<Category> findAllByOrderByIdAsc();
}

