package com.fsoft.khoahn.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.fsoft.khoahn.repository.entity.ComponentEntity;

public interface ComponentRepo extends JpaRepository<ComponentEntity, Integer> {
    ComponentEntity findByComponentName(String componentName);
    Page<ComponentEntity> findByComponentNameContaining(String componentName, Pageable pageable);
}
