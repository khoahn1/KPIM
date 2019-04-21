package com.fsoft.khoahn.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fsoft.khoahn.repository.entity.UserAuthorityEntity;
import com.fsoft.khoahn.repository.entity.pk.UserOperationId;

public interface UserAuthorityRepo extends JpaRepository<UserAuthorityEntity, UserOperationId> {

	void deleteByUserId(String id);

}