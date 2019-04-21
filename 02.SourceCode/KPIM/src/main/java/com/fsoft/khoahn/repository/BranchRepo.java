package com.fsoft.khoahn.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.fsoft.khoahn.repository.entity.BranchEntity;

public interface BranchRepo extends JpaRepository<BranchEntity, String> {

	Page<BranchEntity> findByIdContainingAndBranchNameContaining(String id, String branchName, Pageable pageable);

	BranchEntity findByBranchName(String branchName);

}
