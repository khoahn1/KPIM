package com.fsoft.khoahn.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.fsoft.khoahn.repository.entity.LanguageEntity;

public interface LanguageRepo extends JpaRepository<LanguageEntity, Integer> {
	@Override
	Page<LanguageEntity> findAll(Pageable pageable);
}
