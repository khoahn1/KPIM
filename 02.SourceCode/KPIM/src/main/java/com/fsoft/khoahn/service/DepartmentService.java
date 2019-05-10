package com.fsoft.khoahn.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.fsoft.khoahn.dto.req.DepartmentCreateReqDto;
import com.fsoft.khoahn.dto.req.DepartmentDeleteReqDto;
import com.fsoft.khoahn.dto.req.DepartmentReadReqDto;
import com.fsoft.khoahn.dto.req.DepartmentUpdateReqDto;
import com.fsoft.khoahn.dto.res.DepartmentDetailResDto;

public interface DepartmentService {

	DepartmentDetailResDto findOne(String id);

	Page<DepartmentDetailResDto> findAll(DepartmentReadReqDto departmentReadReqDto);

	void save(DepartmentCreateReqDto departmentCreateReqDto);

	void update(DepartmentUpdateReqDto departmentUpdateReqDto);

	void delete(DepartmentDeleteReqDto departmentDeleteReqDto);

	List<DepartmentDetailResDto> getAll();

	boolean isExitDepartmentCode(String departmentCode);
}
