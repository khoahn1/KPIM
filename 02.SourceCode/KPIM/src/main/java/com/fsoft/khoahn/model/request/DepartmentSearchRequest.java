package com.fsoft.khoahn.model.request;

import lombok.Data;

@Data
public class DepartmentSearchRequest {

	private ParentDepartmentDetailRequest parentDepartment;

	private String departmentCode;

	private String departmentName;

}