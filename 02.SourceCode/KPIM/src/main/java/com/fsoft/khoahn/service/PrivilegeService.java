package com.fsoft.khoahn.service;

import java.util.List;

import com.fsoft.khoahn.dto.res.AuthorityDetailResDto;

public interface PrivilegeService {

	List<AuthorityDetailResDto> findAll();
}
