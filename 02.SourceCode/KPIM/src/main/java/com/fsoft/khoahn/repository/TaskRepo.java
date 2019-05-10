package com.fsoft.khoahn.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.fsoft.khoahn.repository.entity.TaskEntity;

public interface TaskRepo extends JpaRepository<TaskEntity, String> {

	TaskEntity findByTaskCode(String taskCode);

	Page<TaskEntity> findByTaskCodeContaining(String taskCode, Pageable pageable);

	TaskEntity findByProductIdAndPhaseIdAndUserId(Integer productId, Integer phaseId, String userId);

}
