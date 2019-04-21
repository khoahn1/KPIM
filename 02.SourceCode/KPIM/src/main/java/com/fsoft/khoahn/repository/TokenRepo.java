package com.fsoft.khoahn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fsoft.khoahn.repository.entity.TokenEntity;

public interface TokenRepo extends JpaRepository<TokenEntity, String> {
}