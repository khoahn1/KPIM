package com.fsoft.khoahn.model.request;

import lombok.Data;

@Data
public class ParentDepartmentUpdateRequest {

	private String id;
	
	private String parentDepartmentName;
	
	private String parentDepartmentCode;
	
	private String description;

	private String status;
	
	private CompanyDetailReqDto company;
}
