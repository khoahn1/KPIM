package com.fsoft.khoahn.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.fsoft.khoahn.dto.req.CompanyCreateReqDto;
import com.fsoft.khoahn.dto.req.CompanyDeleteReqDto;
import com.fsoft.khoahn.dto.req.CompanyReadReqDto;
import com.fsoft.khoahn.dto.req.CompanyUpdateReqDto;
import com.fsoft.khoahn.dto.res.CompanyDetailResDto;

public interface CompanyService {
	
	Page<CompanyDetailResDto> findAll(CompanyReadReqDto companyReadReqDto);

	void save(CompanyCreateReqDto companyCreateReqDto);
	
	boolean isExitCompanyCode(String companyCode);
	
	CompanyDetailResDto findOne(String id);
	
	void update(CompanyUpdateReqDto companyUpdateReqDto);
	
	void delete(CompanyDeleteReqDto companyDeleteReqDto);
	
	List<CompanyDetailResDto> getAll();
}
