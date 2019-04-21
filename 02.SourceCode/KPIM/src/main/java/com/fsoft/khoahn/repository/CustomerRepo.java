package com.fsoft.khoahn.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.fsoft.khoahn.repository.entity.CustomerEntity;

public interface CustomerRepo extends JpaRepository<CustomerEntity, String> {

	Page<CustomerEntity> findByFullNameContainingAndEmailContaining(String fullName, String email, Pageable pageable);
}
