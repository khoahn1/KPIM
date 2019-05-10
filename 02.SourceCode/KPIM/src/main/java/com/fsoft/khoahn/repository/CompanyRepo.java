package com.fsoft.khoahn.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.fsoft.khoahn.repository.entity.CompanyEntity;

public interface CompanyRepo extends JpaRepository<CompanyEntity, String> {

	Page<CompanyEntity> findByIdContainingAndCompanyNameContaining(String id, String companyName, Pageable pageable);

	CompanyEntity findByCompanyCode(String companyCode);
}
