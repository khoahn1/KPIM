package com.fsoft.khoahn.model.request;

import lombok.Data;

@Data
public class ParentDepartmentCreateRequest {

	private String parentDepartmentCode;

	private String parentDepartmentName;

	private String description;

	private String status;
	
	private CompanyDetailReqDto company;
}
