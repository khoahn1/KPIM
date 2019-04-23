package com.fsoft.khoahn.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.fsoft.khoahn.repository.entity.MaritalStatusEntity;

public interface MaritalStatusRepo extends JpaRepository<MaritalStatusEntity, Integer> {
	@Override
	Page<MaritalStatusEntity> findAll(Pageable pageable);
}
