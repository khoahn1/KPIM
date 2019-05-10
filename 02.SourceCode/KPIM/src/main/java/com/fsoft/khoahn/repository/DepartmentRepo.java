package com.fsoft.khoahn.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.fsoft.khoahn.repository.entity.DepartmentEntity;

public interface DepartmentRepo extends JpaRepository<DepartmentEntity, String>{

	Page<DepartmentEntity> findByDepartmentCodeContainingAndDepartmentNameContaining(String departmentCode,
			String departmentName, Pageable pageable);

	DepartmentEntity findByDepartmentCode(String departmentCode);

}
