package com.fsoft.khoahn.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.fsoft.khoahn.dto.req.RoleCreateReqDto;
import com.fsoft.khoahn.dto.req.RoleDeleteReqDto;
import com.fsoft.khoahn.dto.req.RoleReadReqDto;
import com.fsoft.khoahn.dto.req.RoleUpdateReqDto;
import com.fsoft.khoahn.dto.res.AuthorityDetailResDto;
import com.fsoft.khoahn.dto.res.RoleDetailResDto;

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
