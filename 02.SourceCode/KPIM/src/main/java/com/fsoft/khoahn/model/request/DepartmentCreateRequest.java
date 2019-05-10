package com.fsoft.khoahn.model.request;

import lombok.Data;

@Data
public class DepartmentCreateRequest {

	private ParentDepartmentDetailRequest parentDepartment;

	private String departmentCode;

	private String departmentName;

	private String status;
}
