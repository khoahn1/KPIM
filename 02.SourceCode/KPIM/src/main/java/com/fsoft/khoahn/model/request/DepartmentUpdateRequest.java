package com.fsoft.khoahn.model.request;

import lombok.Data;

@Data
public class DepartmentUpdateRequest {

	private String id;
	
	private ParentDepartmentDetailRequest parentDepartment;

	private String departmentName;
	
	private String departmentCode;
	
	private String status;
}
