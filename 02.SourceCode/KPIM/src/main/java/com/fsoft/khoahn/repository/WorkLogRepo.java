package com.fsoft.khoahn.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.fsoft.khoahn.repository.entity.WorkLogEntity;

public interface WorkLogRepo extends JpaRepository<WorkLogEntity, Integer> {
    Page<WorkLogEntity> findByTaskUserUsername(String username, Pageable pageable);
}
