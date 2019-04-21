package com.fsoft.khoahn.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fsoft.khoahn.repository.entity.RoleAuthorityEntity;
import com.fsoft.khoahn.repository.entity.pk.RoleOperationId;

public interface RoleAuthorityRepo extends JpaRepository<RoleAuthorityEntity, RoleOperationId> {

}