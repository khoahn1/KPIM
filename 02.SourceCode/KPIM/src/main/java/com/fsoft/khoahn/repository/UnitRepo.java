package com.fsoft.khoahn.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.fsoft.khoahn.repository.entity.UnitEntity;

public interface UnitRepo extends JpaRepository<UnitEntity, Integer> {

	UnitEntity findByUnitName(String unitName);

	Page<UnitEntity> findByUnitNameContaining(String unitName,
			Pageable pageable);
}
