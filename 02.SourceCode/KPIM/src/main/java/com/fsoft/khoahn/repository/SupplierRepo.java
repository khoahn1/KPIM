package com.fsoft.khoahn.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.fsoft.khoahn.repository.entity.SupplierEntity;

public interface SupplierRepo extends JpaRepository<SupplierEntity, String> {

	Page<SupplierEntity> findBySupplierNameContainingAndEmailContaining(String fullName, String email,
			Pageable pageable);
}
