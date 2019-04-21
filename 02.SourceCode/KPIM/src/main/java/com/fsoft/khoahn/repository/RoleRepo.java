package com.fsoft.khoahn.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.fsoft.khoahn.repository.entity.RoleEntity;

public interface RoleRepo extends JpaRepository<RoleEntity, String> {

	Page<RoleEntity> findByIdContainingAndRoleNameContaining(String id, String roleName, Pageable pageable);

	RoleEntity findByRoleName(String roleName);
}
