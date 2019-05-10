package com.fsoft.khoahn.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.fsoft.khoahn.repository.entity.ProductEntity;

public interface ProductRepo extends JpaRepository<ProductEntity, Integer> {
    ProductEntity findByProductName(String productName);
    Page<ProductEntity> findByProductNameContaining(String productName, Pageable pageable);
}
