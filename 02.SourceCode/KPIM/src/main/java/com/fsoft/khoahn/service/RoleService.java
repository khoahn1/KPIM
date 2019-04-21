package com.fsoft.khoahn.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.fsoft.khoahn.model.request.dto.RoleCreateReqDto;
import com.fsoft.khoahn.model.request.dto.RoleDeleteReqDto;
import com.fsoft.khoahn.model.request.dto.RoleReadReqDto;
import com.fsoft.khoahn.model.request.dto.RoleUpdateReqDto;
import com.fsoft.khoahn.model.response.dto.AuthorityDetailResDto;
import com.fsoft.khoahn.model.response.dto.RoleDetailResDto;

public interface RoleService {

	List<RoleDetailResDto> findAll();

	List<AuthorityDetailResDto> getRoleAuthority();

	Page<RoleDetailResDto> findAll(RoleReadReqDto roleReadReqDto);

	boolean isExitRoleName(String roleName);

	void save(RoleCreateReqDto roleCreateReqDto);

	RoleDetailResDto findOne(String id);

	void delete(RoleDeleteReqDto roleDeleteReqDto);

	void update(RoleUpdateReqDto roleUpdateReqDto);

}
