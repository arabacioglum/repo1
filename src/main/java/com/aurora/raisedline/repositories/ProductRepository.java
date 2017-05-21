package com.aurora.raisedline.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aurora.raisedline.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

	List<Product> findAll();
}
