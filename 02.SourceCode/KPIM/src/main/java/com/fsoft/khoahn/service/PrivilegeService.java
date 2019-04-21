package com.fsoft.khoahn.service;

import java.util.List;

import com.fsoft.khoahn.model.response.dto.AuthorityDetailResDto;

public interface PrivilegeService {

	List<AuthorityDetailResDto> findAll();
}
