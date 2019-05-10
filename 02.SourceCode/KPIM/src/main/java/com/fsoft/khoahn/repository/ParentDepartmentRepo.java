package com.fsoft.khoahn.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.fsoft.khoahn.repository.entity.ParentDepartmentEntity;

public interface ParentDepartmentRepo extends JpaRepository<ParentDepartmentEntity, String> {

	Page<ParentDepartmentEntity> findByParentDepartmentCodeContainingAndParentDepartmentNameContaining(
			String parentDepartmentCode, String parentDepartmentName, Pageable pageable);

	ParentDepartmentEntity findByParentDepartmentCode(String parentDepartmentCode);

}
