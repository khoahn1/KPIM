package com.fsoft.khoahn.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fsoft.khoahn.repository.entity.PrivilegeEntity;

public interface PrivilegeRepo extends JpaRepository<PrivilegeEntity, String> {
}
