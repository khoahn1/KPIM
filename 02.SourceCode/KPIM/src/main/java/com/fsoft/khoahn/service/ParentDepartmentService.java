package com.fsoft.khoahn.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.fsoft.khoahn.dto.req.ParentDepartmentCreateReqDto;
import com.fsoft.khoahn.dto.req.ParentDepartmentDeleteReqDto;
import com.fsoft.khoahn.dto.req.ParentDepartmentReadReqDto;
import com.fsoft.khoahn.dto.req.ParentDepartmentUpdateReqDto;
import com.fsoft.khoahn.dto.res.ParentDepartmentDetailResDto;

public interface ParentDepartmentService {

	ParentDepartmentDetailResDto findOne(String id);

	Page<ParentDepartmentDetailResDto> findAll(ParentDepartmentReadReqDto parentDepartmentReadReqDto);

	void save(ParentDepartmentCreateReqDto parentDepartmentCreateReqDto);

	void update(ParentDepartmentUpdateReqDto parentDepartmentUpdateReqDto);

	void delete(ParentDepartmentDeleteReqDto parentDepartmentDeleteReqDto);

	List<ParentDepartmentDetailResDto> getAll();

	boolean isExitParentDepartmentCode(String parentDepartmentCode);
}
