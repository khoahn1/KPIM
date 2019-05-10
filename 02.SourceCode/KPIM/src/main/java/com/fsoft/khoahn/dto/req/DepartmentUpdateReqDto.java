package com.fsoft.khoahn.dto.req;

import lombok.Data;

@Data
public class DepartmentUpdateReqDto {

	private String id;

	private ParentDepartmentDetailReqDto parentDepartment;

	private String departmentCode;
	
	private String departmentName;

	private String status;
}
