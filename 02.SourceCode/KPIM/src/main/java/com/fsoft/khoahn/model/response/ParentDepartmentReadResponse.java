package com.fsoft.khoahn.model.response;

import java.util.List;

import org.springframework.data.domain.Page;

import lombok.Data;

@Data
public class ParentDepartmentReadResponse {
	private Page<ParentDepartmentDetailResponse> parentDepartments;
	
	private List<CompanyDetailResponse> companies;
}

