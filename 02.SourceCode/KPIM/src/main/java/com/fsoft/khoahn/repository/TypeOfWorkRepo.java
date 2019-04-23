package com.fsoft.khoahn.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.fsoft.khoahn.repository.entity.TypeOfWorkEntity;

public interface TypeOfWorkRepo extends JpaRepository<TypeOfWorkEntity, Integer> {

	TypeOfWorkEntity findByTypeName(String genderName);

	Page<TypeOfWorkEntity> findByTypeNameContaining(String typeOfWorkName, Pageable pageable);
}
