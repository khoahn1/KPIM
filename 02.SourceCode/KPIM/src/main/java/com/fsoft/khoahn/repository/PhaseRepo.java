package com.fsoft.khoahn.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.fsoft.khoahn.repository.entity.PhaseEntity;

public interface PhaseRepo extends JpaRepository<PhaseEntity, Integer> {

	PhaseEntity findByPhaseName(String genderName);

	Page<PhaseEntity> findByPhaseCodeContainingAndPhaseNameContaining(String phaseCode, String phaseName,
			Pageable pageable);
}
