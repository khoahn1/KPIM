package com.fsoft.khoahn.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.fsoft.khoahn.repository.entity.ProjectUserEntity;

public interface ProjectUserRepo extends JpaRepository<ProjectUserEntity, String> {

	Page<ProjectUserEntity> findByProjectId(String projectId, Pageable pageable);

}
