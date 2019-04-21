package com.fsoft.khoahn.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.fsoft.khoahn.repository.entity.UserEntity;

public interface UserRepo extends JpaRepository<UserEntity, String> {
    UserEntity findByUsername(String username);

	Page<UserEntity> findByUsernameContainingAndEmailContaining(String username, String email,
			Pageable pageable);
}
