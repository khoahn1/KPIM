package com.fsoft.khoahn.dto.req;

import com.fsoft.khoahn.model.request.CompanyDetailReqDto;

import lombok.Data;

@Data
public class ParentDepartmentUpdateReqDto {

	private String id;

	private String parentDepartmentCode;
	
	private String parentDepartmentName;

	private String description;

	private String status;
	
	private CompanyDetailReqDto company;
}
