package com.fsoft.khoahn.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.fsoft.khoahn.repository.entity.ProjectEntity;

public interface ProjectRepo extends JpaRepository<ProjectEntity, String>{

	Page<ProjectEntity> findByProjectCodeContainingAndProjectNameContaining(String projectCode, String projectName,
			Pageable pageable);
	
}
