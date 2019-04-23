package com.fsoft.khoahn.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.fsoft.khoahn.repository.entity.GenderEntity;

public interface GenderRepo extends JpaRepository<GenderEntity, Integer> {
	@Override
	Page<GenderEntity> findAll(Pageable pageable);

	GenderEntity findByGenderName(String genderName);
}
