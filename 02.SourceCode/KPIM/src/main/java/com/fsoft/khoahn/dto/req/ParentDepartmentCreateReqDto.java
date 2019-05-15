package com.fsoft.khoahn.dto.req;

import com.fsoft.khoahn.model.request.CompanyDetailReqDto;

import lombok.Data;

@Data
public class ParentDepartmentCreateReqDto {

	private String parentDepartmentCode;
	
	private String parentDepartmentName;

	private String description;

	private String status;
	
	private CompanyDetailReqDto company;
}
