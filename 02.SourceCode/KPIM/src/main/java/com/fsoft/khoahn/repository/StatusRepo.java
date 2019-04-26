package com.fsoft.khoahn.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fsoft.khoahn.repository.entity.StatusEntity;

public interface StatusRepo extends JpaRepository<StatusEntity, Integer> {
}
